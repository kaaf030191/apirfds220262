package com.epiis.apirfds220262.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epiis.apirfds220262.generic.ResponseGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAdminGetAll extends ResponseGeneric {
	public List<Map<String, Object>> listData = new ArrayList<>();
	private long totalPending;
	private long totalSeen;
	private long totalRefused;
	private long totalCoordination;
	private long totalClose;
}
