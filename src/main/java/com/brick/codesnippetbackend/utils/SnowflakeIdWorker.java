package com.brick.codesnippetbackend.utils;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SnowflakeIdWorker {

	@Value("${snowflake.datacenter-id:0}") // 默认值为 0
	private long datacenterId;

	@Value("${snowflake.worker-id:0}") // 默认值为 0
	private long workerId;
	private final Snowflake snowflake;

	SnowflakeIdWorker() {
		snowflake = IdUtil.createSnowflake(datacenterId, workerId);
		log.info("SnowflakeIdWorker started with datacenterId: {}, workerId: {}", datacenterId, workerId);
	}

	public long nextId() {
		// 生成下一个 ID
		return snowflake.nextId();
	}

	public String nextIdStr() {
		// 生成下一个 ID (String 类型)
		return snowflake.nextIdStr();
	}
}
