// author: Bryan Nehl -- k0emt
// reference: http://www.mkyong.com/mongodb/java-mongodb-save-image-example/
// reference: http://docs.mongodb.org/manual/applications/gridfs/

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class GridFsStoreChunk {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("usage: GridFsStore filename color");
			System.exit(1);
		}

		String imageFileName = args[0];
		String imageColor = args[1];
		BasicDBObject metadata = new BasicDBObject("color", imageColor);

		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("imagedb");

			File imageFile = new File(imageFileName);
			GridFS gfsImages = new GridFS(db, "images");
			GridFSInputFile gfsFile = gfsImages.createFile(imageFile);

			gfsFile.setFilename(imageFileName);
			gfsFile.setMetaData(metadata);

			gfsFile.save(4096);

			System.out.printf("%s saved with metadata color: %s\n",
					imageFileName, imageColor);

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
