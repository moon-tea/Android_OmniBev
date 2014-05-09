package com.example.networkcalls;

public class Wine {
	public int id;
	public String name 			;//= "96-West";
	public String img_file_path ;//= "img.myIMG.png";
	public String varietal 		;//= "Tempranillo";
	public String region 		;//= "Texas";
	public String vintage 		;//= "2012";
	public String profile 		;//= "bold, nutty, black-cherry";
	public String color 		;//= "Plum";
	public String alcohol_content;//= "14.5%";
	public String rating 		;//= "5";

	public Wine(
					int id				,
					String name 		,//= "96-West";
					String img_file_path,//= "img.myIMG.png";
					String varietal 	,//= "Tempranillo";
					String region 		,//= "Texas";
					String vintage 		,//= "2012";
					String profile 		,//= "bold, nutty, black-cherry";
					String color 		,//= "Plum";
					String content 		,//= "14.5%";
					String rating 		 //= "5";
				) {
		this.id				= id			;
		this.name 			= name			;//= "96-West";
		this.img_file_path 	= img_file_path	;//= "img.myIMG.png";
		this.varietal 		= varietal		;//= "Tempranillo";
		this.region 		= region		;//= "Texas";
		this.vintage 		= vintage		;//= "2012";
		this.profile 		= profile		;//= "bold, nutty, black-cherry";
		this.color 			= color			;//= "Plum";
		this.alcohol_content= content		;//= "14.5%";
		this.rating 		= rating		;//= "5";
	}

}
