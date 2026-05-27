"""
Main entry point for the enhanced CS-370 Pirate Intelligent Agent project.
"""

import os

import tensorflow as tf

from config import (
    DATA_SIZE,
    EPSILON_DECAY,
    EPSILON_MIN,
    EPSILON_START,
    MAZE,
    MAX_MEMORY_MULTIPLIER,
    MODEL_DIR,
    N_EPOCHS,
    RESULTS_DIR,
    TARGET_UPDATE_FREQ,
)
from trainer import build_model, completion_check, qtrain
from TreasureMaze import TreasureMaze


def main() -> None:
    os.makedirs(RESULTS_DIR, exist_ok=True)
    os.makedirs(MODEL_DIR, exist_ok=True)

    print("Starting enhanced Pirate Intelligent Agent training...")

    model = build_model(MAZE)

    max_memory = MAX_MEMORY_MULTIPLIER * MAZE.size

    metrics = qtrain(
        model=model,
        maze=MAZE,
        n_epoch=N_EPOCHS,
        max_memory=max_memory,
        data_size=DATA_SIZE,
        target_update_freq=TARGET_UPDATE_FREQ,
        epsilon_start=EPSILON_START,
        epsilon_min=EPSILON_MIN,
        epsilon_decay=EPSILON_DECAY,
    )

    print(metrics.summary())

    csv_path = os.path.join(RESULTS_DIR, "training_summary.csv")
    metrics.save_csv(csv_path)

    graph_dir = os.path.join(RESULTS_DIR, "graphs")
    metrics.save_graphs(graph_dir)

    model_path = os.path.join(MODEL_DIR, "pirate_agent_model.keras")
    model.save(model_path)

    qmaze = TreasureMaze(MAZE)
    passed = completion_check(model, qmaze)

    print("Completion check passed:", passed)

    if passed:
        print("The pirate successfully completes the maze from valid starting positions.")
    else:
        print("The pirate does not consistently complete the maze yet.")

    print(f"Metrics saved to: {csv_path}")
    print(f"Graphs saved to: {graph_dir}")
    print(f"Model saved to: {model_path}")


if __name__ == "__main__":
    tf.get_logger().setLevel("ERROR")
    main()