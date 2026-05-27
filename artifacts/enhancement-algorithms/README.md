# Enhancement 2: Algorithms and Data Structures (CS-370)

## Overview
This enhancement focuses on improving the Pirate Intelligent Agent project originally developed in CS-370. The project uses reinforcement learning and deep Q-learning to train an intelligent pirate agent to navigate through a maze environment and locate a treasure.

The enhanced version improves project organization, reinforcement learning evaluation, performance tracking, validation logic, and maintainability while preserving the original reinforcement learning functionality.

---

## Folder Structure

enhancement-algorithms/
│
├── enhanced/
│ ├── models/
│ │ └── pirate_agent_model.keras
│ │
│ ├── results/
│ │ ├── graphs/
│ │ │ ├── loss_by_episode.png
│ │ │ ├── reward_by_episode.png
│ │ │ ├── steps_by_episode.png
│ │ │ └── win_rate_by_episode.png
│ │ │
│ │ └── training_summary.csv
│ │
│ ├── config.py
│ ├── GameExperience.py
│ ├── main.py
│ ├── metrics.py
│ ├── trainer.py
│ ├── TreasureMaze.py
│ ├── requirements.txt
│ └── README.md
│
├── original/
│ ├── GameExperience.py
│ ├── Shank_Brandon_ProjectTwoMilestone.ipynb
│ ├── Shank_Brandon_ProjectTwoMilestone_Rev2.html
│ ├── TreasureHuntGame_starterCode.ipynb
│ ├── TreasureMaze.py
│ ├── CS-370 Intelligent Agent Design Defense.docx
│ └── README.md
│
├── screenshots/
│ ├── completion_validation_output.png
│ ├── csv_metrics_1.png
│ ├── csv_metrics_2.png
│ ├── enhanced_project_structure.png
│ ├── loss_by_episode.png
│ ├── reward_by_episode.png
│ ├── saved_model_output.png
│ ├── steps_by_episode.png
│ ├── training_execution_output_1.png
│ ├── training_execution_output_2.png
│ └── win_rate_by_episode.png
│
└── Algorithms and Data Structures Enhancement.docx

---

## Original Version
The original version demonstrates reinforcement learning concepts using deep Q-learning and neural networks to train a pirate agent to solve a maze environment.

However, it has:
- Limited project organization
- Minimal performance tracking
- Limited evaluation reporting
- Basic completion validation
- Notebook-based structure that is harder to maintain

This version is included for comparison purposes.

---

## Enhancements Implemented

### 1. Modular Project Structure
- Converted notebook implementation into modular `.py` files
- Improved readability and maintainability
- Added separated execution, training, configuration, and metrics logic

### 2. Reinforcement Learning Metrics
- Added tracking for:
  - Loss
  - Rewards
  - Steps
  - Epsilon values
  - Win rates

### 3. Automated Graph Generation
- Added graph generation for:
  - Loss by episode
  - Reward by episode
  - Steps by episode
  - Win-rate progression

### 4. CSV Metric Export
- Training metrics exported to CSV format
- Allows reinforcement learning analysis outside the program

### 5. Improved Completion Validation
- Added stricter validation logic
- Verifies the pirate agent can consistently solve the maze from multiple starting positions

### 6. Hyperparameter Configuration
- Added configurable settings for:
  - Epsilon decay
  - Replay memory
  - Batch size
  - Target network synchronization

### 7. Model Persistence
- Automatically saves trained reinforcement learning model using `.keras` format
- Allows trained models to be reused without retraining

### 8. Maintainability Improvements
- Improved comments and readability
- Cleaner project organization
- Improved debugging support

---

## Running the Project

To run the project:

```bash
pip install -r requirements.txt
python main.py