"""
Configuration settings for the enhanced CS-370 Pirate Intelligent Agent project.
"""

import numpy as np


MAZE = np.array([
    [1., 0., 1., 1., 1., 1., 1., 1.],
    [1., 0., 1., 1., 1., 0., 1., 1.],
    [1., 1., 1., 1., 0., 1., 0., 1.],
    [1., 1., 1., 0., 1., 1., 1., 1.],
    [1., 1., 0., 1., 1., 1., 1., 1.],
    [1., 1., 1., 0., 1., 0., 0., 0.],
    [1., 1., 1., 0., 1., 1., 1., 1.],
    [1., 1., 1., 1., 0., 1., 1., 1.]
])


LEFT = 0
UP = 1
RIGHT = 2
DOWN = 3

ACTIONS_DICT = {
    LEFT: "left",
    UP: "up",
    RIGHT: "right",
    DOWN: "down",
}

NUM_ACTIONS = len(ACTIONS_DICT)


# Training settings
N_EPOCHS = 999
MAX_MEMORY_MULTIPLIER = 8
DATA_SIZE = 32
TARGET_UPDATE_FREQ = 50


# Epsilon-greedy settings
EPSILON_START = 1.0
EPSILON_MIN = 0.01
EPSILON_DECAY = 0.995


# Output folders
RESULTS_DIR = "enhanced/results"
MODEL_DIR = "enhanced/models"