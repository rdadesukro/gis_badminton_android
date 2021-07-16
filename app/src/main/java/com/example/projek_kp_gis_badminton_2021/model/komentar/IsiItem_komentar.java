package com.example.projek_kp_gis_badminton_2021.model.komentar;

import com.google.gson.annotations.SerializedName;

public class IsiItem_komentar {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("komentar")
	private String komentar;

	@SerializedName("jenis_id")
	private int jenisId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setKomentar(String komentar){
		this.komentar = komentar;
	}

	public String getKomentar(){
		return komentar;
	}

	public void setJenisId(int jenisId){
		this.jenisId = jenisId;
	}

	public int getJenisId(){
		return jenisId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"IsiItem{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",komentar = '" + komentar + '\'' + 
			",jenis_id = '" + jenisId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}