package com.ymw.exam.demo.vo;

import java.util.LinkedList;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoom {
	private String roomNumber;
	private String roomName;
	private LinkedList<String> users;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatRoom other = (ChatRoom) obj;
		return Objects.equals(roomNumber, other.roomNumber);
	}
	@Override
	public int hashCode() {
		return Objects.hash(roomNumber);
	}
}
