package fr.gaulupeau.apps.Poche.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.gaulupeau.apps.Poche.network.FeedUpdater;

public class ServiceHelper {

    private static final String TAG = ServiceHelper.class.getSimpleName();

    // TODO: reuse code

    public static void syncQueue(Context context) {
        syncQueue(context, false, false, null);
    }

    public static void syncQueue(Context context, boolean auto) {
        syncQueue(context, auto, false, null);
    }

    public static void syncQueue(Context context, boolean auto,
                                 boolean byOperation, Long queueLength) {
        Log.d(TAG, "syncQueue() started");

        ActionRequest request = new ActionRequest(ActionRequest.Action.SyncQueue);
        if(auto) request.setRequestType(ActionRequest.RequestType.Auto);
        else if(byOperation) request.setRequestType(ActionRequest.RequestType.ManualByOperation);
        if(queueLength != null) request.setQueueLength(queueLength);

        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "syncQueue() finished");
    }

    public static void addLink(Context context, String link) {
        addLink(context, link, null);
    }

    public static void addLink(Context context, String link, Long operationID) {
        Log.d(TAG, "archiveArticle() started");

        ActionRequest request = new ActionRequest(ActionRequest.Action.AddLink);
        request.setLink(link);
        request.setOperationID(operationID);

        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "archiveArticle() finished");
    }

    public static void archiveArticle(Context context, int articleID, boolean archive) {
        Log.d(TAG, "archiveArticle() started");

        ActionRequest request = new ActionRequest(
                archive ? ActionRequest.Action.Archive : ActionRequest.Action.Unarchive);
        request.setArticleID(articleID);

        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "archiveArticle() finished");
    }

    public static void favoriteArticle(Context context, int articleID, boolean favorite) {
        Log.d(TAG, "favoriteArticle() started");

        ActionRequest request = new ActionRequest(
                favorite ? ActionRequest.Action.Favorite : ActionRequest.Action.Unfavorite);
        request.setArticleID(articleID);

        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "favoriteArticle() finished");
    }

    public static void deleteArticle(Context context, int articleID) {
        Log.d(TAG, "deleteArticle() started");

        ActionRequest request = new ActionRequest(ActionRequest.Action.Delete);
        request.setArticleID(articleID);

        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "deleteArticle() finished");
    }

    public static void updateFeed(Context context,
                                  FeedUpdater.FeedType feedType,
                                  FeedUpdater.UpdateType updateType) {
        updateFeed(context, feedType, updateType, null, false);
    }

    public static void updateFeed(Context context,
                                  FeedUpdater.FeedType feedType,
                                  FeedUpdater.UpdateType updateType,
                                  Long operationID, boolean auto) {
        Log.d(TAG, "updateFeed() started");

        ActionRequest request = new ActionRequest(ActionRequest.Action.UpdateFeed);
        request.setFeedUpdateFeedType(feedType);
        request.setFeedUpdateUpdateType(updateType);
        request.setOperationID(operationID);
        if(auto) request.setRequestType(ActionRequest.RequestType.Auto);

        Intent intent = new Intent(context, MainService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "updateFeed() finished");
    }

    public static void downloadArticleAsPDF(Context context, int articleID, Long operationID) {
        downloadArticleAsFile(context, articleID, ActionRequest.DownloadFormat.PDF, operationID);
    }

    public static void downloadArticleAsFile(Context context, int articleID,
                                             ActionRequest.DownloadFormat downloadFormat,
                                             Long operationID) {
        Log.d(TAG, "downloadArticleAsFile() started");

        ActionRequest request = new ActionRequest(ActionRequest.Action.DownloadAsFile);
        request.setArticleID(articleID);
        request.setDownloadFormat(downloadFormat);
        request.setOperationID(operationID);

        Intent intent = new Intent(context, SecondaryService.class);
        intent.putExtra(ActionRequest.ACTION_REQUEST, request);

        context.startService(intent);

        Log.d(TAG, "downloadArticleAsFile() finished");
    }

}
