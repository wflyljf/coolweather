package util;

import model.City;
import model.Country;
import model.province;
import android.text.TextUtils;
import db.CoolWeatherDB;

public class Utility {
	//�����ʹ������������ص�ʡ������
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
		if (!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0){
				for (String p : allProvinces){
					String[] array = p.split("\\|");
					province province = new province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// ���������������ݴ洢��Province��
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	//�����ʹ������������ص��м�����
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response, int provinceId){
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
						String[] array = c.split("\\|");
						City city = new City();
						city.setCityCode(array[0]);
						city.setCityName(array[1]);
						city.setProvinceId(provinceId);
						// ���������������ݴ洢��City��
						coolWeatherDB.saveCity(city);
					}
					return true;
				}
			}
		return false;
	}
	
	//�����ʹ������������ص��ؼ�����
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int cityId) {
			if (!TextUtils.isEmpty(response)) {
				String[] allCounties = response.split(",");
				if (allCounties != null && allCounties.length > 0) {
					for (String c : allCounties) {
							String[] array = c.split("\\|");
							Country county = new Country();
							county.setCountyCode(array[0]);
							county.setCountyName(array[1]);
							county.setCityId(cityId);
							// ���������������ݴ洢��County��
							coolWeatherDB.saveCounty(county);
						}
						return true;
					}
				}
			return false;
		}
}