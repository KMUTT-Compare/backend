package sit.int371.capstoneproject.autoId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.entities.Staff;
import sit.int371.capstoneproject.repositories.StaffRepository;


@Service
public class SequenceGeneratorService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class);

        // หาก Sequence ถูกสร้างใหม่ ให้เริ่มจาก staffId ที่มากที่สุด
        if (counter != null && counter.getSeq() == 1) {
            int maxStaffId = staffRepository.findTopByOrderByStaffIdDesc()
                    .map(Staff::getStaffId)
                    .orElse(0);
            counter.setSeq(maxStaffId + 1);

            // อัปเดตค่า seq ใหม่ในฐานข้อมูล
            mongoOperations.save(counter);
        }

        return counter != null ? counter.getSeq() : 1;
    }
}
