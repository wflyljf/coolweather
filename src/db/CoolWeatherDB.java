package db;
import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Country;
import model.province;
import db.CoolWeatherOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class CoolWeatherDB {
	
	// 数据库名
	public static final String DB_NAME = "cool_weather";
	

	//数据库版本
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	//将构造方法私有化
	
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();  //创建或打开数据库
		}
	
	//获取CoolWeatherDB的实例
	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
			return coolWeatherDB;
		}
	//将Province实例存储到数据库
	public void saveProvince(province province) {
		if (province != null) {
				ContentValues values = new ContentValues();
				values.put("province_name", province.getProvinceName());
				values.put("province_code", province.getProvinceCode());
				db.insert("Province", null, values);
			}
		}
	
	//从数据库读取全国所有的省份信息。
	public List<province> loadProvince(){
		List<province> list = new ArrayList<province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		
		if(cursor.moveToFirst()){
			do{
				province province = new province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while (cursor.moveToNext());
		}
		return list;
	}
	//City实例存储到数据库。
	public void saveCity(City city) {
		if (city != null) {
				ContentValues values = new ContentValues();
				values.put("city_name", city.getCityName());
				values.put("city_code", city.getCityCode());
				values.put("province_id", city.getProvinceId());
				db.insert("City", null, values);
			}
		}
	//从数据库读取某省下所有的城市信息。
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
					City city = new City();
					city.setId(cursor.getInt(cursor.getColumnIndex("id")));
					city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
					city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
					city.setProvinceId(provinceId);
					list.add(city);
				} while (cursor.moveToNext());
			}
			return list;
		}
	//将County实例存储到数据库。
	public void saveCounty(Country county) {
		if (county != null) {
				ContentValues values = new ContentValues();
				values.put("county_name", county.getCountyName());
				values.put("county_code", county.getCountyCode());
				values.put("city_id", county.getCityId());
				db.insert("County", null, values);
			}
		}
	//从数据库读取某城市下所有的县信息
	public List<Country> loadCounties(int cityId) {
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.query("County", null, "city_id = ?",new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
				do {
					Country county = new Country();
					county.setId(cursor.getInt(cursor.getColumnIndex("id")));
					county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
					county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
					county.setCityId(cityId);
					list.add(county);
				} while (cursor.moveToNext());
			}
			return list;
		}
}
