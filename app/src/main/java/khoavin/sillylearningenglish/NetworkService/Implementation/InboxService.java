package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;
import android.util.SparseArray;

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
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_CLAIM;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_DELETE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_GET_ATTACH_ITEMS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_GET_ITEMS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_MASK_OPENED;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_RATE;

public class InboxService implements IInboxService {

    /**
     * Storage current inbox items
     */
    private ArrayList<Inbox> _items;

    /**
     * value indicate inbox updated state.
     */
    private boolean inboxUpdated = false;

    /**
     * The attach item map.
     */
    private HashMap<Integer, ArrayList<AttachItem>> attachItemsMap;

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
                                    Inbox[] items = JsonConvert.getArray(response, Inbox[].class);
                                    if (items != null) {
                                        _items = ArrayConvert.toArrayList(items);
                                        receiver.onSuccess(_items);
                                    } else {
                                        _items = null;
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    _items = null;
                                    Common.LogError("Can not parse response as Mail list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                                    if (responseCodes != null && responseCodes.length > 0) {
                                        receiver.onSuccess(responseCodes[0]);
                                        inboxUpdated = true;
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as Rating response code list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                                    if (responseCodes != null && responseCodes.length > 0) {
                                        receiver.onSuccess(responseCodes[0]);
                                        RemoveItemFormView(mail_id);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as Remove mail response code list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
     * Mask mail as opened state
     *
     * @param user_id The user's Identifier
     * @param mail_id The mail's Identifier
     */
    @Override
    public void MaskAsOpened(final String user_id, final int mail_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> receiver) {
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
                                    if (responseCodes != null && responseCodes.length > 0) {
                                        receiver.onSuccess(responseCodes[0]);
                                        inboxUpdated = true;
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as Mask as opened mail response code list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                                    if (responseCodes != null && responseCodes.length > 0) {
                                        receiver.onSuccess(responseCodes[0]);
                                        inboxUpdated = true;
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as claim reward response code");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
    public ArrayList<Inbox> GetCurrentInboxItem() {
        return _items;
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
                                        AttachItem[] attachItems = JsonConvert.getArray(response, AttachItem[].class);
                                        if (attachItems != null) {
                                            ArrayList<AttachItem> ListItems = ArrayConvert.toArrayList(attachItems);
                                            AddAttachItemToMail(ListItems, mail_id);
                                            receiver.onSuccess(ListItems);
                                        } else {
                                            receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    } catch (JsonParseException ex) {
                                        Common.LogError("Can not parse response as AttachItems");
                                        Common.LogError(ex.toString());
                                        try {
                                            ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                            if (error != null && error.length > 0)
                                                receiver.onError(error[0]);
                                            else
                                                receiver.onError(Common.getNotFoundErrorCode());
                                        } catch (JsonParseException ex_error) {
                                            receiver.onError(Common.getParseJsonErrorCode());
                                            Common.LogError("Can not parse response as error code");
                                            Common.LogError(ex_error.toString());
                                        }
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

    /**
     * Remove item from adapter.
     *
     * @param mail_id The mail id.
     */
    @Override
    public void RemoveItemFormView(int mail_id) {
        inboxUpdated = true;
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
     * Gets a value indicate when inbox items has updated.
     *
     * @return true if inbox items has changed, otherwise return false.
     */
    @Override
    public boolean IsInboxUpdated() {
        return inboxUpdated;
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
                inboxUpdated = true;
                break;
            }
        }
    }

    /**
     * Set inbox to up to date state.
     */
    @Override
    public void SetInboxToUpToDate()
    {
        inboxUpdated = false;
    }

    //Set the attach items.
    private void setAttachItems(int mailId, ArrayList<AttachItem> items) {
        if(attachItemsMap == null) {
            attachItemsMap = new HashMap<Integer, ArrayList<AttachItem>>();
        }

        if(!attachItemsMap.containsKey(mailId))
        {
            attachItemsMap.put(mailId, items);
        }
    }

    //Get the attach items.
    private ArrayList<AttachItem> getAttachItems(int mailId) {
        if(attachItemsMap == null) return null;
        if(!attachItemsMap.containsKey(mailId)) return null;
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
        Inbox mail = null;
        for (int i = 0; i < _items.size(); i++) {
            mail = _items.get(i);
            if (mail.getId() == mail_id) {
                return getAttachItems(mail_id);
            }
        }

        return null;
    }
}
