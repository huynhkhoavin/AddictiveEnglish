package khoavin.sillylearningenglish.Function;

import android.view.View;

import java.util.HashMap;

/**
 * Created by OatOal on 5/9/2017.
 */

public class UIView {

    private HashMap<String, View> _data;

    public UIView()
    {
        _data = new HashMap<String, View>();
    }

    public boolean RegistryState(String key, View value)
    {
        if(!_data.containsKey(key) && !_data.containsValue(value))
        {
            _data.put(key, value);
            return  true;
        }
        return false;
    }

    /**
     * Set control state
     * @param state
     */
    public void ControlState(String state)
    {
        for(HashMap.Entry<String, View> entry : _data.entrySet()) {
            String key = entry.getKey();
            View value = entry.getValue();
            if(key.equals(state))
            {
                value.setVisibility(View.VISIBLE);
            }
            else
            {
                value.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Get control
     * @param key
     * @return
     */
    public View GetControl(String key)
    {
        if(_data.containsKey(key))
            return _data.get(key);
        return null;
    }

    /**
     * Active control with key
     * @param key
     * @return
     */
    public boolean ActiveControl(String key)
    {
        if(_data.containsKey(key))
        {
            _data.get(key).setVisibility(View.VISIBLE);
            return  true;
        }
        return false;
    }

    /**
     * Deactive control with key
     * @param key
     * @return
     */
    public boolean DeactiveControl(String key)
    {
        if(_data.containsKey(key))
        {
            _data.get(key).setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * Deactive all control
     */
    public void DeactiveAllcontrol()
    {
        for(HashMap.Entry<String, View> entry : _data.entrySet()) {
            String key = entry.getKey();
            View value = entry.getValue();
            value.setVisibility(View.GONE);
        }
    }

    /**
     * Active all control
     */
    public void ActiveAllControl()
    {
        for(HashMap.Entry<String, View> entry : _data.entrySet()) {
            String key = entry.getKey();
            View value = entry.getValue();
            value.setVisibility(View.VISIBLE);
        }
    }
}
