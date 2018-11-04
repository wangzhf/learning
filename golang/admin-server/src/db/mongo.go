package db

import (
	"context"
	"log"

	"github.com/mongodb/mongo-go-driver/bson"

	"github.com/mongodb/mongo-go-driver/mongo"
)

// AddUser is a
func AddUser() {
	client, err := mongo.Connect(context.Background(), "mongodb://140.143.55.90:27017", nil)

	if err != nil {
		log.Println(err.Error())
		return
	}

	db := client.Database("demo")
	queryTopLevelFields(db)
}

func queryTopLevelFields(db *mongo.Database) {
	// _, err := db.RunCommand(
	// 	context.Background(),
	// 	bson.NewDocument(bson.EC.Int32("dropDatabase", 1)),
	// )
	coll := db.Collection("test")
	{
		result, err := coll.InsertOne(
			context.Background(),
			bson.NewDocument(
				bson.EC.String("item", "canvas"),
				bson.EC.Int32("qty", 100),
				bson.EC.ArrayFromElements("tags",
					bson.VC.String("cotton"),
				),
				bson.EC.SubDocumentFromElements("size",
					bson.EC.Int32("h", 28),
					bson.EC.Double("w", 35.5),
					bson.EC.String("uom", "cm"),
				),
			))
		log.Println(result)
		if err != nil {
			log.Printf(err.Error())
		}
	}

	collName := coll.Name
	log.Println("collName: ", collName())

}
