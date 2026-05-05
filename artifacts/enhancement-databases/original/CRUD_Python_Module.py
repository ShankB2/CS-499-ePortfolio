from pymongo import MongoClient
from pymongo.errors import PyMongoError


class AnimalShelter(object):
    """
    CRUD operations for the 'animals' collection in MongoDB.
    This class handles creating, reading, updating, and deleting records.
    """

    def __init__(self, username="aacuser", password="aacpassword"):
        """
        Initialize the MongoDB connection using authentication.
        Connects to the 'aac' database and 'animals' collection.
        """

        # Connection configuration
        self.USER = username
        self.PASS = password
        self.HOST = "localhost"
        self.PORT = 27017
        self.DB = "aac"
        self.COL = "animals"

        try:
            # Create MongoDB client with authentication
            self.client = MongoClient(
                f"mongodb://{self.USER}:{self.PASS}@{self.HOST}:{self.PORT}/?authSource=admin"
            )

            # Verify connection is successful
            self.client.admin.command("ping")

            # Access database and collection
            self.database = self.client[self.DB]
            self.collection = self.database[self.COL]

        except PyMongoError as e:
            print("Connection error:", e)
            self.client = None
            self.database = None
            self.collection = None

    def create(self, data):
        """
        Insert a document into the collection.

        Parameters:
            data (dict): The document to insert

        Returns:
            bool: True if successful, False otherwise
        """
        try:
            if self.collection is not None and isinstance(data, dict):
                self.collection.insert_one(data)
                return True
            return False
        except PyMongoError as e:
            print("Create error:", e)
            return False

    def read(self, query, projection=None):
        """
        Retrieve documents from the collection.

        Parameters:
            query (dict): MongoDB query filter
            projection (dict, optional): Fields to include/exclude

        Returns:
            list: List of matching documents
        """
        try:
            if self.collection is not None and isinstance(query, dict):
                projection = projection if projection else {}
                results = self.collection.find(query, projection)
                return list(results)
            return []
        except PyMongoError as e:
            print("Read error:", e)
            return []

    def update(self, query, new_values):
        """
        Update existing document(s) in the collection.

        Parameters:
            query (dict): Filter to match documents
            new_values (dict): Values to update

        Returns:
            int: Number of documents modified
        """
        try:
            if (
                self.collection is not None
                and isinstance(query, dict)
                and isinstance(new_values, dict)
            ):
                result = self.collection.update_many(query, {"$set": new_values})
                return result.modified_count
            return 0
        except PyMongoError as e:
            print("Update error:", e)
            return 0

    def delete(self, query):
        """
        Delete document(s) from the collection.

        Parameters:
            query (dict): Filter to match documents

        Returns:
            int: Number of documents deleted
        """
        try:
            if self.collection is not None and isinstance(query, dict):
                result = self.collection.delete_many(query)
                return result.deleted_count
            return 0
        except PyMongoError as e:
            print("Delete error:", e)
            return 0