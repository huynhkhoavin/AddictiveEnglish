package khoavin.sillylearningenglish.NetworkService.Interfaces;

/**
 * Created by KhoaVin on 19/06/2017.
 */

public interface ISharedPreferences {
    void SavePreferences(String Key, Object Value);
    Object LoadPreferences(String Key);
}
