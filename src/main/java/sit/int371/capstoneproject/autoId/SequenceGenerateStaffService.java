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
public class SequenceGenerateStaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        // ดึง Sequence ปัจจุบัน (ถ้าไม่มี ให้สร้างใหม่)
        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                DatabaseSequence.class);

        // ดึงค่า staffId สูงสุดใน collection staff
        int maxStaffId = staffRepository.findTopByOrderByStaffIdDesc()
                .map(Staff::getStaffId)
                .orElse(0);

        // หาก Sequence เริ่มใหม่หรือมีค่าน้อยกว่า staffId สูงสุด ให้รีเซ็ต Sequence
        if (counter == null || counter.getSeq() <= maxStaffId) {
            long newSeq = maxStaffId + 1; // กำหนดค่าใหม่ให้มากกว่า staffId สูงสุด
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