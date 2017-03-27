package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;

/**
 * Created by OatOal on 3/27/2017.
 */

public interface IInboxService {

    /// <Sumary>
    /// Gets the inbox items
    /// </Sumary>
    void GetInboxItems(String user_id, IServerResponse<Inboxs> receiver);

}
