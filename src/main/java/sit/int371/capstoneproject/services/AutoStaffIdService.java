package sit.int371.capstoneproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import sit.int371.capstoneproject.autoId.AutoStaffId;

public class AutoStaffIdService {
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        AutoStaffId counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                AutoStaffId.class);

        return counter != null ? counter.getSeq() : 1;
    }
}
