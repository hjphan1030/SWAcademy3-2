package com.cnu.sw2023.hj.repository;

import com.cnu.sw2023.hj.entity.testboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<testboard, Integer> { // 어떤 엔티티를 넣을 것이냐, 엔티티의 id의 타입
}
