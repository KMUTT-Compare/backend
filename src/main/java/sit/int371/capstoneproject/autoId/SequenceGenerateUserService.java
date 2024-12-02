package sit.int371.capstoneproject.autoId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import sit.int371.capstoneproject.entities.User;
import sit.int371.capstoneproject.repositories.UserRepository;

@Service
public class SequenceGenerateUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        // ดึง Sequence ปัจจุบัน (ถ้าไม่มี ให้สร้างใหม่)
        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class);

        // ดึงค่า userId สูงสุดใน collection user
        int maxUserId = userRepository.findTopByOrderByUserIdDesc()
                .map(User::getUserId)
                .orElse(1);

        // หาก Sequence เริ่มใหม่หรือมีค่าน้อยกว่า userId สูงสุด ให้รีเซ็ต Sequence
        if (counter == null || counter.getSeq() <= maxUserId) {
            long newSeq = maxUserId + 1; // กำหนดค่าใหม่ให้มากกว่า userId สูงสุด
            mongoOperations.updateFirst(
                    Query.query(Criteria.where("_id").is(seqName)),
                    Update.update("seq", newSeq),
                    DatabaseSequence.class);

            return newSeq; // ส่งคืนค่า Sequence ที่อัปเดตแล้ว
        }

        // ส่งคืน Sequence ปัจจุบันที่เพิ่มขึ้น 1
        return counter != null ? counter.getSeq() : 1;
    }
}
