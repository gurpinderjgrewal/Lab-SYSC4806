package org.example.lab2_sysc4806;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuddyInfoRepo extends CrudRepository<BuddyInfo, Long> {
    List<BuddyInfo> findBuddyInfoByName(String name);
    BuddyInfo findBuddyInfoById(Long id);
}
