package com.goayo.debtify.model;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-10-07
 *
 * 2020-10-13 Modified by Olof Sjögren: removed event parameter from method. Is now only a notification.
 */

public interface IEventHandler {
    void onModelEvent();
}