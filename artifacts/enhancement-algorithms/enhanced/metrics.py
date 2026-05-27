"""
Performance tracking and reporting for the enhanced Pirate Intelligent Agent.
"""

import csv
import os
from dataclasses import dataclass, asdict
from typing import List

import matplotlib.pyplot as plt


@dataclass
class EpisodeMetric:
    epoch: int
    loss: float
    steps: int
    reward: float
    result: str
    epsilon: float
    win_rate: float


class TrainingMetrics:
    """
    Tracks training progress so the enhanced artifact can show measurable
    improvement and clearer evaluation results.
    """

    def __init__(self) -> None:
        self.history: List[EpisodeMetric] = []

    def add(
        self,
        epoch: int,
        loss: float,
        steps: int,
        reward: float,
        result: str,
        epsilon: float,
        win_rate: float,
    ) -> None:
        self.history.append(
            EpisodeMetric(
                epoch=epoch,
                loss=loss,
                steps=steps,
                reward=reward,
                result=result,
                epsilon=epsilon,
                win_rate=win_rate,
            )
        )

    def win_count(self) -> int:
        return sum(1 for item in self.history if item.result == "win")

    def loss_count(self) -> int:
        return sum(1 for item in self.history if item.result == "lose")

    def total_episodes(self) -> int:
        return len(self.history)

    def average_steps(self) -> float:
        if not self.history:
            return 0.0
        return sum(item.steps for item in self.history) / len(self.history)

    def average_reward(self) -> float:
        if not self.history:
            return 0.0
        return sum(item.reward for item in self.history) / len(self.history)

    def final_win_rate(self) -> float:
        if not self.history:
            return 0.0
        return self.history[-1].win_rate

    def summary(self) -> str:
        return (
            "\nTraining Summary\n"
            "----------------\n"
            f"Total episodes: {self.total_episodes()}\n"
            f"Wins: {self.win_count()}\n"
            f"Losses: {self.loss_count()}\n"
            f"Average steps: {self.average_steps():.2f}\n"
            f"Average reward: {self.average_reward():.2f}\n"
            f"Final win rate: {self.final_win_rate():.3f}\n"
        )

    def save_csv(self, file_path: str) -> None:
        os.makedirs(os.path.dirname(file_path), exist_ok=True)

        with open(file_path, "w", newline="", encoding="utf-8") as csvfile:
            fieldnames = [
                "epoch",
                "loss",
                "steps",
                "reward",
                "result",
                "epsilon",
                "win_rate",
            ]
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()

            for item in self.history:
                writer.writerow(asdict(item))

    def save_graphs(self, output_dir: str) -> None:
        os.makedirs(output_dir, exist_ok=True)

        if not self.history:
            return

        epochs = [item.epoch for item in self.history]
        losses = [item.loss for item in self.history]
        rewards = [item.reward for item in self.history]
        steps = [item.steps for item in self.history]
        win_rates = [item.win_rate for item in self.history]

        self._save_line_chart(
            epochs,
            losses,
            "Training Loss by Episode",
            "Episode",
            "Loss",
            os.path.join(output_dir, "loss_by_episode.png"),
        )

        self._save_line_chart(
            epochs,
            rewards,
            "Reward by Episode",
            "Episode",
            "Reward",
            os.path.join(output_dir, "reward_by_episode.png"),
        )

        self._save_line_chart(
            epochs,
            steps,
            "Steps by Episode",
            "Episode",
            "Steps",
            os.path.join(output_dir, "steps_by_episode.png"),
        )

        self._save_line_chart(
            epochs,
            win_rates,
            "Win Rate by Episode",
            "Episode",
            "Win Rate",
            os.path.join(output_dir, "win_rate_by_episode.png"),
        )

    @staticmethod
    def _save_line_chart(x, y, title, xlabel, ylabel, file_path) -> None:
        plt.figure()
        plt.plot(x, y)
        plt.title(title)
        plt.xlabel(xlabel)
        plt.ylabel(ylabel)
        plt.tight_layout()
        plt.savefig(file_path)
        plt.close()