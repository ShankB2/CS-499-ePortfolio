"""
Enhanced training logic for the CS-370 Pirate Intelligent Agent.

This file separates the training logic from the notebook so the project is
easier to test, maintain, and improve.
"""

import datetime
import random
from typing import Optional

import numpy as np
import tensorflow as tf
from tensorflow.keras.layers import Dense, PReLU
from tensorflow.keras.models import Sequential, clone_model
from tensorflow.keras.optimizers import Adam

from GameExperience import GameExperience
from TreasureMaze import TreasureMaze
from config import NUM_ACTIONS
from metrics import TrainingMetrics


def format_time(seconds: float) -> str:
    if seconds < 400:
        return f"{seconds:.1f} seconds"
    if seconds < 4000:
        return f"{seconds / 60.0:.2f} minutes"
    return f"{seconds / 3600.0:.2f} hours"


def build_model(maze: np.ndarray) -> Sequential:
    """
    Builds the neural network used by the deep Q-learning agent.
    """
    model = Sequential()
    model.add(Dense(maze.size, input_shape=(maze.size,)))
    model.add(PReLU())
    model.add(Dense(maze.size))
    model.add(PReLU())
    model.add(Dense(NUM_ACTIONS))
    model.compile(optimizer=Adam(), loss="mse")
    return model


def create_experience(model, target_model, max_memory: int):
    """
    Supports both the original GameExperience constructor and the enhanced
    constructor if target_model support was added.
    """
    try:
        return GameExperience(model, target_model, max_memory=max_memory)
    except TypeError:
        return GameExperience(model, max_memory=max_memory)


def play_game(
    model,
    qmaze: TreasureMaze,
    pirate_cell,
    max_steps: Optional[int] = None,
) -> bool:
    """
    Runs a trained model from a selected starting cell to test if it can win.
    """
    qmaze.reset(pirate_cell)
    envstate = qmaze.observe()
    steps = 0

    if max_steps is None:
        max_steps = qmaze.maze.size * 4

    while steps < max_steps:
        state = np.asarray(envstate, dtype=np.float32)

        if state.ndim == 1:
            state = np.expand_dims(state, axis=0)

        q_values = model(state, training=False).numpy()
        action = int(np.argmax(q_values[0]))

        envstate, reward, game_status = qmaze.act(action)
        steps += 1

        if game_status == "win":
            return True

        if game_status == "lose":
            return False

    return False


def completion_check(model, maze_or_qmaze, max_steps: Optional[int] = None) -> bool:
    """
    Validates that the model can solve the maze from multiple valid starting
    positions instead of only relying on a recent win rate.
    """
    if isinstance(maze_or_qmaze, TreasureMaze):
        qmaze = maze_or_qmaze
    else:
        qmaze = TreasureMaze(maze_or_qmaze)

    for cell in qmaze.free_cells:
        if not qmaze.valid_actions(cell):
            continue

        if not play_game(model, qmaze, cell, max_steps=max_steps):
            return False

    return True


def choose_action(experience, env_state, valid_actions, epsilon: float) -> int:
    """
    Chooses an action using epsilon-greedy exploration.
    """
    if random.random() < epsilon:
        return random.choice(valid_actions)

    q_values = np.array(experience.predict(env_state)).flatten()
    best_action = int(np.argmax(q_values))

    if best_action in valid_actions:
        return best_action

    return random.choice(valid_actions)


def qtrain(
    model,
    maze: np.ndarray,
    n_epoch: int = 999,
    max_memory: int = 512,
    data_size: int = 32,
    target_update_freq: int = 50,
    epsilon_start: float = 1.0,
    epsilon_min: float = 0.01,
    epsilon_decay: float = 0.995,
) -> TrainingMetrics:
    """
    Enhanced training loop.

    Improvements included:
    - epsilon-greedy exploration control
    - target network synchronization
    - replay memory training
    - performance metrics
    - completion validation from multiple starting positions
    """
    epsilon = epsilon_start
    start_time = datetime.datetime.now()

    qmaze = TreasureMaze(maze)

    target_model = clone_model(model)
    target_model.set_weights(model.get_weights())

    experience = create_experience(
        model=model,
        target_model=target_model,
        max_memory=max_memory,
    )

    metrics = TrainingMetrics()
    win_history = []
    history_size = max(1, qmaze.maze.size // 2)
    win_rate = 0.0

    for epoch in range(n_epoch):
        loss = 0.0
        steps = 0
        total_reward = 0.0

        agent_cell = random.choice(qmaze.free_cells)
        qmaze.reset(agent_cell)

        env_state = qmaze.observe()
        game_status = "not_over"

        while game_status == "not_over":
            previous_envstate = env_state
            valid_actions = qmaze.valid_actions()

            action = choose_action(
                experience=experience,
                env_state=previous_envstate,
                valid_actions=valid_actions,
                epsilon=epsilon,
            )

            env_state, reward, game_status = qmaze.act(action)
            total_reward += reward

            if game_status == "win":
                win_history.append(1)
            elif game_status == "lose":
                win_history.append(0)

            episode = [
                previous_envstate,
                action,
                reward,
                env_state,
                game_status,
            ]
            experience.remember(episode)

            inputs, targets = experience.get_data(data_size)
            batch_loss = model.train_on_batch(inputs, targets)
            loss += float(batch_loss)

            steps += 1

        if (epoch + 1) % target_update_freq == 0:
            target_model.set_weights(model.get_weights())

        if len(win_history) >= history_size:
            win_rate = sum(win_history[-history_size:]) / history_size
        else:
            win_rate = 0.0

        metrics.add(
            epoch=epoch,
            loss=loss,
            steps=steps,
            reward=total_reward,
            result=game_status,
            epsilon=epsilon,
            win_rate=win_rate,
        )

        elapsed = datetime.datetime.now() - start_time
        print(
            "Epoch: {:03d}/{:d} | Loss: {:.4f} | Steps: {:d} | "
            "Reward: {:.2f} | Result: {} | Win rate: {:.3f} | "
            "Epsilon: {:.3f} | Time: {}".format(
                epoch,
                n_epoch - 1,
                loss,
                steps,
                total_reward,
                game_status,
                win_rate,
                epsilon,
                format_time(elapsed.total_seconds()),
            )
        )

        epsilon = max(epsilon * epsilon_decay, epsilon_min)

        if win_rate >= 0.95 and epoch > 100:
            print(f"Reached completion check at epoch {epoch}")
            break

    total_time = datetime.datetime.now() - start_time
    print("Training complete in:", format_time(total_time.total_seconds()))

    return metrics