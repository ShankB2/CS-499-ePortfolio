from pymongo import MongoClient, ASCENDING
from pymongo.errors import PyMongoError


class AnimalShelter(object):
    """
    Enhanced CRUD operations for the 'animals' collection in MongoDB.

    Enhancements include:
    - Full CRUD support
    - Input validation
    - Error handling
    - Authenticated MongoDB connection
    - Flexible filtering
    - Rescue-specific database filtering
    - Aggregation support
    - Indexing for performance
    """

    def __init__(self, username="aacuser", password="aacpassword"):
        self.USER = username
        self.PASS = password
        self.HOST = "localhost"
        self.PORT = 27017
        self.DB = "aac"
        self.COL = "animals"

        try:
            self.client = MongoClient(
                f"mongodb://{self.USER}:{self.PASS}@{self.HOST}:{self.PORT}/?authSource=admin",
                serverSelectionTimeoutMS=5000
            )

            self.client.admin.command("ping")

            self.database = self.client[self.DB]
            self.collection = self.database[self.COL]

            self.create_indexes()

        except PyMongoError as e:
            print("Connection error:", e)
            self.client = None
            self.database = None
            self.collection = None

    def _is_connected(self):
        return self.collection is not None

    def _validate_dict(self, value, name):
        if not isinstance(value, dict) or not value:
            raise ValueError(f"{name} must be a non-empty dictionary.")

    def create(self, data):
        try:
            if not self._is_connected():
                return False

            self._validate_dict(data, "data")

            result = self.collection.insert_one(data)
            return result.acknowledged

        except (PyMongoError, ValueError) as e:
            print("Create error:", e)
            return False

    def read(self, query=None, projection=None):
        try:
            if not self._is_connected():
                return []

            query = query or {}

            if not isinstance(query, dict):
                raise ValueError("query must be a dictionary.")

            if projection is not None and not isinstance(projection, dict):
                raise ValueError("projection must be a dictionary.")

            results = self.collection.find(query, projection)
            return list(results)

        except (PyMongoError, ValueError) as e:
            print("Read error:", e)
            return []

    def update(self, query, new_values):
        try:
            if not self._is_connected():
                return 0

            self._validate_dict(query, "query")
            self._validate_dict(new_values, "new_values")

            result = self.collection.update_many(query, {"$set": new_values})
            return result.modified_count

        except (PyMongoError, ValueError) as e:
            print("Update error:", e)
            return 0

    def delete(self, query):
        try:
            if not self._is_connected():
                return 0

            self._validate_dict(query, "query")

            result = self.collection.delete_many(query)
            return result.deleted_count

        except (PyMongoError, ValueError) as e:
            print("Delete error:", e)
            return 0

    def find_with_filters(self, filters=None, projection=None, limit=0):
        try:
            if not self._is_connected():
                return []

            filters = filters or {}
            projection = projection or {"_id": 0}

            if not isinstance(filters, dict):
                raise ValueError("filters must be a dictionary.")

            if not isinstance(projection, dict):
                raise ValueError("projection must be a dictionary.")

            results = self.collection.find(filters, projection)

            if isinstance(limit, int) and limit > 0:
                results = results.limit(limit)

            return list(results)

        except (PyMongoError, ValueError) as e:
            print("Filter error:", e)
            return []

    def rescue_filter(self, rescue_type):
        water_rescue = {
            "animal_type": "Dog",
            "breed": {
                "$in": [
                    "Labrador Retriever Mix",
                    "Chesapeake Bay Retriever",
                    "Newfoundland"
                ]
            },
            "sex_upon_outcome": "Intact Female",
            "age_upon_outcome_in_weeks": {"$gte": 26, "$lte": 156}
        }

        mountain_rescue = {
            "animal_type": "Dog",
            "breed": {
                "$in": [
                    "German Shepherd",
                    "Alaskan Malamute",
                    "Old English Sheepdog",
                    "Siberian Husky",
                    "Rottweiler"
                ]
            },
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte": 26, "$lte": 156}
        }

        disaster_rescue = {
            "animal_type": "Dog",
            "breed": {
                "$in": [
                    "Doberman Pinscher",
                    "German Shepherd",
                    "Golden Retriever",
                    "Bloodhound",
                    "Rottweiler"
                ]
            },
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte": 20, "$lte": 300}
        }

        filters = {
            "Water": water_rescue,
            "Mountain": mountain_rescue,
            "Disaster": disaster_rescue,
            "Reset": {}
        }

        return self.find_with_filters(filters.get(rescue_type, {}))

    def aggregate_by_breed(self):
        try:
            if not self._is_connected():
                return []

            pipeline = [
                {"$match": {"breed": {"$exists": True, "$ne": ""}}},
                {"$group": {"_id": "$breed", "count": {"$sum": 1}}},
                {"$sort": {"count": -1}}
            ]

            return list(self.collection.aggregate(pipeline))

        except PyMongoError as e:
            print("Aggregation error:", e)
            return []

    def aggregate_by_outcome_type(self):
        try:
            if not self._is_connected():
                return []

            pipeline = [
                {"$match": {"outcome_type": {"$exists": True, "$ne": ""}}},
                {"$group": {"_id": "$outcome_type", "count": {"$sum": 1}}},
                {"$sort": {"count": -1}}
            ]

            return list(self.collection.aggregate(pipeline))

        except PyMongoError as e:
            print("Aggregation error:", e)
            return []

    def create_indexes(self):
        try:
            if not self._is_connected():
                return False

            self.collection.create_index([("breed", ASCENDING)])
            self.collection.create_index([("outcome_type", ASCENDING)])
            self.collection.create_index([("sex_upon_outcome", ASCENDING)])
            self.collection.create_index([("age_upon_outcome_in_weeks", ASCENDING)])
            self.collection.create_index([("animal_type", ASCENDING)])

            return True

        except PyMongoError as e:
            print("Index error:", e)
            return False

    def count_documents(self, query=None):
        try:
            if not self._is_connected():
                return 0

            query = query or {}

            if not isinstance(query, dict):
                raise ValueError("query must be a dictionary.")

            return self.collection.count_documents(query)

        except (PyMongoError, ValueError) as e:
            print("Count error:", e)
            return 0

    def close_connection(self):
        try:
            if self.client:
                self.client.close()
        except PyMongoError as e:
            print("Close connection error:", e)