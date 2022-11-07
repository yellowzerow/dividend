package com.zerobase.dividend.persist;

import com.zerobase.dividend.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
