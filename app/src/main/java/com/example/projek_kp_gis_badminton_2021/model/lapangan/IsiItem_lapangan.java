package com.example.projek_kp_gis_badminton_2021.model.lapangan;

import com.google.gson.annotations.SerializedName;

public class IsiItem_lapangan {


	public float getRaiting() {
		return raiting;
	}

	public void setRaiting(float raiting) {
		this.raiting = raiting;
	}

	@SerializedName("raiting")
	private float raiting;

	@SerializedName("nama")
	private String nama;


	@SerializedName("status")
	private int status;


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus_all() {
		return status_all;
	}

	public void setStatus_all(int status_all) {
		this.status_all = status_all;
	}

	@SerializedName("status_all")
	private int status_all;

	@SerializedName("foto")
	private String foto;

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@SerializedName("lng")
	private double lng;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("phone")
	private String phone;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("lat")
	private double lat;

	@SerializedName("alamat")
	private String alamat;





	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}


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

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
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


	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"IsiItem{" + 
			"raiting = '" + raiting + '\'' + 
			",nama = '" + nama + '\'' + 
			",foto = '" + foto + '\'' + 
			",lng = '" + lng + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",phone = '" + phone + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",lat = '" + lat + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}