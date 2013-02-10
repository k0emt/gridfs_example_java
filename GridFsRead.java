// author: Bryan Nehl -- k0emt
// reference: http://www.mkyong.com/mongodb/java-mongodb-save-image-example/
// reference: http://docs.mongodb.org/manual/applications/gridfs/

import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class GridFsRead {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("usage: GridFsRead color");
		}

		String imageColor = args[0];
		String imageFileName = imageColor + ".png";
		BasicDBObject query = new BasicDBObject("metadata.color", imageColor);
		// this example code utilizes the optional metadata field
		// you could also query on any of the other fields like _id or filename

		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("imagedb");

			GridFS gfsImages = new GridFS(db, "images");
			GridFSDBFile retrievedImage = gfsImages.findOne(query);
			retrievedImage.writeTo(imageFileName);

			System.out.printf("%s created\n", imageFileName);

			mongo.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}