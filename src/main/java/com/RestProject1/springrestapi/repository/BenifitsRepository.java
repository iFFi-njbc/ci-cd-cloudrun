package com.RestProject1.springrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RestProject1.springrestapi.model.Benifits;

@Repository
public interface BenifitsRepository extends JpaRepository<Benifits, Long> {

}
