# Enhanced Artifact - CS-340 Database Enhancement

## Overview
This folder contains the enhanced version of the Grazioso Salvare Rescue Dog Dashboard project originally developed in CS-340.

The enhanced version improves database structure, filtering logic, maintainability, scalability, query performance, and overall organization of the application.

---

## Included Files

### CRUD_Python_Module.py
Enhanced CRUD Python module containing:
- reusable filtering methods,
- aggregation support,
- MongoDB indexing,
- validation logic,
- improved exception handling,
- and modular database-layer organization.

### ProjectTwoDashboard.ipynb
Enhanced Jupyter Dash dashboard application with improved database integration, reusable filtering support, dynamic visualization updates, and improved interaction between dashboard components.

### Grazioso Salvare Logo.png
Logo image used within the dashboard interface.

---

## Major Enhancements

### Database-Layer Filtering
Added reusable filtering methods including:
- `find_with_filters()`
- `rescue_filter()`

These methods improve modularity and reduce repeated query logic throughout the dashboard application.

---

### Aggregation Support
Added MongoDB aggregation pipeline methods for:
- breed analysis,
- outcome analysis,
- and summarized reporting functionality.

---

### Performance Improvements
Implemented MongoDB indexing on commonly searched fields to improve:
- filtering speed,
- query efficiency,
- and scalability.

---

### Validation and Error Handling
Added:
- input validation,
- exception handling,
- and improved database reliability protections.

---

### Maintainability Improvements
Improved software organization by separating:
- dashboard interface logic
from
- reusable database-layer operations.

This created a cleaner and more scalable software structure.

---

## Technologies Used
- Python
- MongoDB
- PyMongo
- Jupyter Dash
- Plotly
- Dash Leaflet
- Pandas