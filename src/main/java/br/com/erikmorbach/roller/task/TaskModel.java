package br.com.erikmorbach.roller.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "tb_task")
public class TaskModel{
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	@Column(length = 50)
	private String title;
	private String description;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String sticker;

	private UUID userId;

	@CreationTimestamp
	private LocalDateTime createdAt;


	public void setNullProp(TaskModel oth){
		if(this.id == null) this.id = oth.id;
		if(this.title == null) this.title = oth.title;
		if(this.description == null) this.description = oth.description;
		if(this.startAt == null) this.startAt = oth.startAt;
		if(this.endAt == null) this.endAt = oth.endAt;
		if(this.sticker == null) this.sticker = oth.sticker;
		if(this.userId == null) this.userId = oth.userId;
		if(this.createdAt == null) this.createdAt = oth.createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws Exception {
		if(title.length() > 50) 
			throw new Exception("Titulo com mais de 50 Caracteres");
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public String getSticker() {
		return sticker;
	}

	public void setSticker(String sticker) {
		this.sticker = sticker;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
