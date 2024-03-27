package com.sjiwon.springtransaction.model

import org.springframework.data.jpa.repository.JpaRepository

interface BucketRepository : JpaRepository<Bucket, Long>
