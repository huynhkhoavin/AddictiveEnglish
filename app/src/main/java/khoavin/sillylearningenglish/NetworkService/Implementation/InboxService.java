package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AttachItem;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_ACCEPT_FRIEND;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_CLAIM;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_DELETE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_DELETE_SELECTED;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_GET_ATTACH_ITEMS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_GET_ITEMS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_MASK_OPENED;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_RATE;

public class InboxService implements IInboxService {

    public InboxService() {
        attachItemsMap = new HashMap<Integer, ArrayList<AttachItem>>();
    }

    //region properties

    /**
     * The attach item map.
     */
    private HashMap<Integer, ArrayList<AttachItem>> attachItemsMap;

    /**
     * The filter item.
     */
    //private HashMap<Common.FilterType, ArrayList<Inbox>> filterItems;

    /**
     * Storage current inbox items
     */
    private ArrayList<Inbox> _items;

    private ArrayList<Inbox> _itemsChallenge;

    private ArrayList<Inbox> _itemsNotUnboxed;

    private ArrayList<Inbox> _itemsSystemInfo;

    private ArrayList<Inbox> _itemsRating;

    private ArrayList<Inbox> _itemsGift;

    private ArrayList<Inbox> _otherMessage;

    /**
     * value indicate inbox updated state.
     */
    private boolean _needUpdate = false;

    //endregion

    //region implementations

    /**
     * Get inbox items
     *
     * @param user_id The user's identifier
     */
    @Override
    public void GetInboxItems(final String user_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ArrayList<Inbox>> receiver) {

        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_GET_ITEMS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        receiver.onError(responseCodes[0]);
                                        _items = null;
                                    } else {
                                        Inbox[] items = JsonConvert.getArray(response, Inbox[].class);
                                        if (items != null) {
                                            _items = ArrayConvert.toArrayList(items);
                                            UnCheckAllMail();
                                            if (attachItemsMap != null) attachItemsMap.clear();
                                            UpdateFilterItems();
                                            receiver.onSuccess(_items);
                                        } else {
                                            _items = null;
                                            receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();

    }

    /**
     * Rate mail from user's inbox
     *
     * @param user_id The user's identifier
     * @param mail_id The mail's identifier
     */
    @Override
    public void RateMail(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_RATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        receiver.onSuccess(responseCodes[0]);
                                        SetRatingStateForItem(mail_id);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("mail_id", String.valueOf(mail_id));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }

    /**
     * Remove mail from user's inbox
     *
     * @param user_id The user's identifier
     * @param mail_id The mail's identifier
     */
    @Override
    public void RemoveMail(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_DELETE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        receiver.onSuccess(responseCodes[0]);
                                        RemoveItemFormView(mail_id);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("mail_id", String.valueOf(mail_id));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }

    @Override
    public void RemoveSelectedMail(final String user_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> volleyResponse) {

        if (_items == null) return;
        ArrayList<Integer> checkedMails = new ArrayList<Integer>();
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getIsChecked()) {
                Common.MailType mailType = _items.get(i).getMailType();
                if (mailType != Common.MailType.BATTLE_CHALLENGE || _items.get(i).getIsReceived()) {
                    checkedMails.add(_items.get(i).getId());
                    Log.e("ITEM CHECKED: ", String.valueOf(i));
                }

            }
        }

        if (checkedMails.size() > 0) {
            String selected = Common.removeCharAt(checkedMails.toString(), 0);
            if (selected.length() > 0)
                selected = Common.removeCharAt(selected, selected.length() - 1);

            final String delMails = selected;
            final ArrayList<Integer> removedItems = checkedMails;

            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_DELETE_SELECTED,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {

                                            ErrorCode err = responseCodes[0];
                                            if (err.getCode() == Common.ServiceCode.USER_NOT_FOUND) {
                                                volleyResponse.onError(err);
                                            } else if (err.getCode() == Common.ServiceCode.COMPLETED) {
                                                RemoveDeletedItems(removedItems);
                                                volleyResponse.onSuccess(err);
                                            } else {
                                                volleyResponse.onError(Common.getNotFoundErrorCode());
                                            }
                                        } else {
                                            volleyResponse.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    } catch (JsonParseException ex) {
                                        volleyResponse.onError(Common.getParseJsonErrorCode());
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error");
                            volleyResponse.onError(Common.getInternalServerErrorCode(error));
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", user_id);
                            params.put("delmails_id", delMails);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }

                @Override
                public void onTaskComplete(Void aVoid) {

                }
            };

            progressAsyncTask.execute();
        } else {
            volleyResponse.onSuccess(Common.getSuccess200ErrorCode());
        }
    }

    /**
     * Mask mail as opened state
     *
     * @param user_id The user's Identifier
     * @param mail_id The mail's Identifier
     */
    @Override
    public void MaskAsOpened(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> receiver) {

        if (IsItemOpened(mail_id)) {
            receiver.onSuccess(Common.getSuccess200ErrorCode());
            return;
        }

        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_MASK_OPENED,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        SetMailStateToJustRead(mail_id);
                                        receiver.onSuccess(responseCodes[0]);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("mail_id", String.valueOf(mail_id));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }

    @Override
    public void ClaimReward(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_CLAIM,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        receiver.onSuccess(responseCodes[0]);
                                        SetItemClaimedState(mail_id);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("mail_id", String.valueOf(mail_id));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }

    /**
     * Get current inbox items
     *
     * @return The Current inbox items
     */
    @Override
    public ArrayList<Inbox> GetCurrentInboxItem(Common.FilterType type) {
        if (type == Common.FilterType.CHALLENGE_MAIL) {
            return _itemsChallenge;
        } else if (type == Common.FilterType.NEW) {
            return _items;
        } else if (type == Common.FilterType.NOTUNBOXED) {
            return _itemsNotUnboxed;
        } else if (type == Common.FilterType.SYSTEM_INFO) {
            return _itemsSystemInfo;
        } else if (type == Common.FilterType.RATING) {
            return _itemsRating;
        } else if (type == Common.FilterType.GIFS) {
            return _itemsGift;
        } else if (type == Common.FilterType.OTHER) {
            return _otherMessage;
        } else {
            return _items;
        }
    }

    /**
     * Remove item from inbox
     *
     * @param mail_identifier The mail ID
     * @return
     */
    @Override
    public ArrayList<Inbox> RemoveItem(int mail_identifier) {
        for (Inbox item : _items) {
            if (item.getId() == mail_identifier) {
                _items.remove(item);
                break;
            }
        }
        return _items;
    }

    /**
     * Remove item from inbox items
     *
     * @param mail_item The item will be removed
     * @return The Inbox items after removed item
     */
    @Override
    public ArrayList<Inbox> RemoveItem(Inbox mail_item) {
        if (_items.contains(mail_item)) {
            _items.remove(mail_item);
        }
        return _items;
    }

    /**
     * Gets the attach items of mail
     *
     * @param user_id The user's identifier
     * @param mail_id The mail's identifier
     * @return
     */
    @Override
    public void GetAttachItems(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ArrayList<AttachItem>> receiver) {

        ArrayList<AttachItem> result = GetAttachItemsWithMailId(mail_id);
        if (result != null) {
            receiver.onSuccess(result);
        } else {
            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_GET_ATTACH_ITEMS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                            receiver.onError(responseCodes[0]);
                                            _items = null;
                                        } else {
                                            AttachItem[] attachItems = JsonConvert.getArray(response, AttachItem[].class);
                                            if (attachItems != null) {
                                                ArrayList<AttachItem> ListItems = ArrayConvert.toArrayList(attachItems);
                                                AddAttachItemToMail(ListItems, mail_id);
                                                receiver.onSuccess(ListItems);
                                            } else {
                                                receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                            }
                                        }
                                    } catch (JsonParseException ex) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error");
                            receiver.onError(Common.getInternalServerErrorCode(error));
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", user_id);
                            params.put("mail_id", String.valueOf(mail_id));
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }

                @Override
                public void onTaskComplete(Void aVoid) {

                }
            };

            progressAsyncTask.execute();
        }
    }

    @Override
    public void AcceptFriendRequest(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> volleyResponse) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_ACCEPT_FRIEND,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        volleyResponse.onSuccess(responseCodes[0]);
                                        SetItemClaimedState(mail_id);
                                    } else {
                                        volleyResponse.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    volleyResponse.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        volleyResponse.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("mail_id", String.valueOf(mail_id));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }

    /**
     * Remove item from adapter.
     *
     * @param mail_id The mail id.
     */
    @Override
    public void RemoveItemFormView(int mail_id) {
        _needUpdate = true;
        int index = -1;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                index = i;
                break;
            }
        }
        _items.remove(index);
    }

    /**
     * remove deleted items.
     *
     * @param delMails
     */
    private void RemoveDeletedItems(ArrayList<Integer> delMails) {
        _needUpdate = true;
        for (int i = 0; i < _items.size(); i++) {
            if (delMails.contains(_items.get(i).getId())) {
                _items.remove(i);
                i--;
            }
        }

        attachItemsMap.clear();
        UpdateFilterItems();
    }

    /**
     * Gets a value indicate when inbox items has updated.
     *
     * @return true if inbox items has changed, otherwise return false.
     */
    @Override
    public boolean IsInboxNeedUpdate() {
        return _needUpdate;
    }

    /**
     * Update inbox items.
     *
     * @param item The updated item.
     */
    @Override
    public void UpdateInboxItem(Inbox item) {
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == item.getId()) {
                _items.set(i, item);
                _needUpdate = true;
                break;
            }
        }
    }

    /**
     * Set inbox to up to date state.
     */
    @Override
    public void SetInboxToUpToDate() {
        _needUpdate = false;
    }

    @Override
    public void setInboxCheckedState(int mail_id, boolean isChecked) {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                _items.get(i).setIsChecked(isChecked);
                break;
            }
        }
    }

//    @Override
//    public void deleteAllCheckedMail(String user_id, Context context, IVolleyService volleyService, IServerResponse<ErrorCode> response) {
//
//
//    }

    @Override
    public void UnCheckAllMail() {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            _items.get(i).setIsChecked(false);
        }
        _needUpdate = true;
    }

    @Override
    public void CheckedAllMail(Common.FilterType type) {
        if (_items == null) return;
        UncheckAllMail();
        ArrayList<Inbox> its = getCurrentFilterItems(type);
        for (int i = 0; i < its.size(); i++) {
            its.get(i).setIsChecked(true);
        }
        _needUpdate = true;
    }

    //endregion

    //region private method

    /**
     * Set rating state for item.
     */
    private void SetRatingStateForItem(int mail_id) {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                _needUpdate = true;
                _items.get(i).setRatingState();
                if (_items.get(i).getIsRated()) {
                    _itemsRating.add(_items.get(i));
                } else {
                    _itemsRating.remove(_items.get(i));
                }
                break;
            }
        }
    }

    /**
     * Set mail state to just read.
     *
     * @param mail_id
     */
    private void SetMailStateToJustRead(int mail_id) {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                _items.get(i).setMailStateToJustRead();
                _needUpdate = true;
                _itemsNotUnboxed.remove(_items.get(i));
                break;
            }
        }
    }

    /**
     * Gets value indicate opened state of inbox items.
     *
     * @return
     */
    private boolean IsItemOpened(int mail_id) {
        if (_items == null) return false;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id)
                return _items.get(i).IsRead();
        }

        return false;
    }

    //Set the attach items.
    private void setAttachItems(int mailId, ArrayList<AttachItem> items) {
        if (!attachItemsMap.containsKey(mailId)) {
            attachItemsMap.put(mailId, items);
        }
    }

    //Get the attach items.
    private ArrayList<AttachItem> getAttachItems(int mailId) {
        if (attachItemsMap == null) return null;
        if (!attachItemsMap.containsKey(mailId)) return null;
        return attachItemsMap.get(mailId);
    }

    /**
     * Add attach items to inbox item.
     *
     * @param items   The attach items.
     * @param mail_id The mail identifier.
     */
    private void AddAttachItemToMail(ArrayList<AttachItem> items, int mail_id) {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                setAttachItems(mail_id, items);
                break;
            }
        }
    }

    /**
     * Gets the attach items of mail.
     *
     * @param mail_id The mail's identifier
     * @return The attach items of with mail identifier.
     */
    private ArrayList<AttachItem> GetAttachItemsWithMailId(int mail_id) {
        if (_items == null) return null;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                return getAttachItems(mail_id);
            }
        }

        return null;
    }

    private void SetItemClaimedState(int mail_id) {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            if (_items.get(i).getId() == mail_id) {
                _needUpdate = true;
                _items.get(i).setReceivedState();
                break;
            }
        }

    }

    /**
     * Update current filter items.
     */
    private void UpdateFilterItems() {

        //Check condition.
        if (_items == null)
            return;

        //Initialize.
        if (_itemsChallenge == null) {
            _itemsSystemInfo = new ArrayList<Inbox>();
            _itemsChallenge = new ArrayList<Inbox>();
            _itemsRating = new ArrayList<Inbox>();
            _itemsNotUnboxed = new ArrayList<Inbox>();
            _itemsGift = new ArrayList<Inbox>();
            _otherMessage = new ArrayList<Inbox>();
        }
        //Clear filter items.
        _itemsSystemInfo.clear();
        _itemsChallenge.clear();
        _itemsRating.clear();
        _itemsNotUnboxed.clear();
        _itemsGift.clear();
        _otherMessage.clear();

        //Add items to filter items.
        for (int i = 0; i < _items.size(); i++) {
            //Add rating mails
            if (_items.get(i).getIsRated()) {
                _itemsRating.add(_items.get(i));
            }

            //Add not unboxed mails.
            if (!_items.get(i).IsRead()) {
                _itemsNotUnboxed.add(_items.get(i));
            }


            if (_items.get(i).getMailType() == Common.MailType.BATTLE_CHALLENGE) {
                //Add challenge mails
                _itemsChallenge.add(_items.get(i));
            } else if (_items.get(i).getMailType() == Common.MailType.BATTLE_RESULT || _items.get(i).getMailType() == Common.MailType.GIF_REWARD) {
                //Add Gift items
                _itemsGift.add(_items.get(i));
            } else if (_items.get(i).getMailType() == Common.MailType.SYSTEM_MESSAGE) {
                //Add system info messages.
                _itemsSystemInfo.add(_items.get(i));
            } else {
                //Add other message.
                _otherMessage.add(_items.get(i));
            }
        }

    }

    /**
     * Get filter items.
     *
     * @param type
     * @return
     */
    private ArrayList<Inbox> getCurrentFilterItems(Common.FilterType type) {
        switch (type) {
            case CHALLENGE_MAIL:
                return _itemsChallenge;
            case GIFS:
                return _itemsGift;
            case NEW:
                return _items;
            case NOTUNBOXED:
                return _itemsNotUnboxed;
            case OTHER:
                return _otherMessage;
            case RATING:
                return _itemsRating;
            case SYSTEM_INFO:
                return _itemsSystemInfo;
            default:
                return _items;
        }
    }

    private void UncheckAllMail() {
        if (_items == null) return;
        for (int i = 0; i < _items.size(); i++) {
            _items.get(i).setIsChecked(false);
        }
        _needUpdate = true;
    }

    //endregion
}
