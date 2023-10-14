package com.github.alaverdyanani.msocial.repository;

import com.github.alaverdyanani.msocial.entity.DailyDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyDomainRepository extends JpaRepository<DailyDomain,Long> {

}
