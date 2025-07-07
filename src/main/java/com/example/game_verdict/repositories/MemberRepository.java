package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, Long> {
}
