//package com.chanpinzazhi.db;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.chanpinzazhi.entity.Category;
//import com.chanpinzazhi.entity.Photo;
//import com.chanpinzazhi.entity.Product;
//
///**
// * 数据模拟类
// * 
// * @author Administrator
// * 
// */
//public class DataControl {
//	public static List<Category> getCategoryList() {
//		List<Category> categorysList = new ArrayList<Category>();
//		Category categroy01 = new Category(
//				1,
//				"椰子猪肚鸡",
//				"先自来水冲洗外面，然后把猪肚翻过来洗里面，洗里面的时候是把猪肚放在盆里（不要放水），加面粉三勺，用两只手猛抓猪肚，抓呀抓，然后用水冲干净，反复两三次",
//				"http://172.16.13.60:8080/cp/cp1.png",
//				"2014-02-18");
//		categorysList.add(categroy01);
//		Category categroy02 = new Category(
//				2,
//				"椰子猪肚鸡",
//				"先自来水冲洗外面，然后把猪肚翻过来洗里面，洗里面的时候是把猪肚放在盆里（不要放水），加面粉三勺，用两只手猛抓猪肚，抓呀抓，然后用水冲干净，反复两三次",
//				"http://172.16.13.60:8080/cp/cp2.png",
//				"2014-02-18");
//		categorysList.add(categroy02);
//		Category categroy03 = new Category(
//				3,
//				"椰子猪肚鸡",
//				"先自来水冲洗外面，然后把猪肚翻过来洗里面，洗里面的时候是把猪肚放在盆里（不要放水），加面粉三勺，用两只手猛抓猪肚，抓呀抓，然后用水冲干净，反复两三次",
//				"http://172.16.13.60:8080/cp/cp3.png",
//				"2014-02-18");
//		categorysList.add(categroy03);
//		Category categroy04 = new Category(
//				4,
//				"椰子猪肚鸡",
//				"先自来水冲洗外面，然后把猪肚翻过来洗里面，洗里面的时候是把猪肚放在盆里（不要放水），加面粉三勺，用两只手猛抓猪肚，抓呀抓，然后用水冲干净，反复两三次",
//				"http://172.16.13.60:8080/cp/cp4.png",
//				"2014-02-18");
//		categorysList.add(categroy04);
//		Category categroy05 = new Category(
//				5,
//				"椰子猪肚鸡",
//				"先自来水冲洗外面，然后把猪肚翻过来洗里面，洗里面的时候是把猪肚放在盆里（不要放水），加面粉三勺，用两只手猛抓猪肚，抓呀抓，然后用水冲干净，反复两三次",
//				"http://172.16.13.60:8080/cp/cp5.png",
//				"2014-02-18");
//		categorysList.add(categroy05);
//		return categorysList;
//	}
//
//	public static List<Product> getPdList() {
//		List<Product> pdList = new ArrayList<Product>();
//		Product product01 = new Product(
//				1,
//				1,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro1.png",
//				"2014-02-14");
//		pdList.add(product01);
//		Product product02 = new Product(
//				2,
//				1,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro2.png",
//				"2014-02-14");
//		pdList.add(product02);
//		Product product03 = new Product(
//				3,
//				1,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro4.png",
//				"2014-02-14");
//		pdList.add(product03);
//		Product product04 = new Product(
//				4,
//				2,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro4.png",
//				"2014-02-14");
//		pdList.add(product04);
//
//		Product product05 = new Product(
//				5,
//				2,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro1.png",
//				"2014-02-14");
//		pdList.add(product05);
//		
//		Product product06 = new Product(
//				6,
//				3,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro2.png",
//				"2014-02-14");
//		pdList.add(product06);
//		Product product07 = new Product(
//				7,
//				3,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro1.png",
//				"2014-02-14");
//		pdList.add(product07);
//		Product product08 = new Product(
//				8,
//				4,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro2.png",
//				"2014-02-14");
//		pdList.add(product08);
//		Product product09 = new Product(
//				9,
//				4,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro3.png",
//				"2014-02-14");
//		pdList.add(product09);
//
//		Product product10 = new Product(
//				10,
//				4,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro4.png",
//				"2014-02-14");
//		pdList.add(product10);
//		
//		Product product11 = new Product(
//				11,
//				4,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro1.png",
//				"2014-02-14");
//		pdList.add(product11);
//
//		Product product12 = new Product(
//				12,
//				4,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro2.png",
//				"2014-02-14");
//		pdList.add(product12);
//		
//		
//		Product product13 = new Product(
//				13,
//				5,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro3.png",
//				"2014-02-14");
//		pdList.add(product13);
//
//		Product product14 = new Product(
//				14,
//				5,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro4.png",
//				"2014-02-14");
//		pdList.add(product14);
//		
//		Product product15 = new Product(
//				15,
//				5,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/pro1.png",
//				"2014-02-14");
//		pdList.add(product15);
//
//		Product product16 = new Product(
//				16,
//				5,
//				"1190",
//				"中国好产品",
//				"EIMS犀牛云",
//				"http://172.16.13.60:8080/cp/cp2.png",
//				"2014-02-14");
//		pdList.add(product16);
//		
//		return pdList;
//	}
//	
//	public static List<Photo> getPhotoList() {
//		List<Photo> photoList = new ArrayList<Photo>();
//		for(int i=1;i<=16;i++){
//			for(int k=1;k<=7;k++){
//				Photo photo = new Photo(i,"http://172.16.13.60:8080/cp/pic"+k+".png","为自己代言");
//				photoList.add(photo);
//			}
//		}
//		return photoList;
//	}
//
//	
//}
