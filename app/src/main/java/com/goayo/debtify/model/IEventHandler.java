package com.goayo.debtify.model;

/**
 * @Author Oscar Sanner and Olof Sjögren
 * @date 2020-10-07
 */

public interface IEventHandler {
    void onModelEvent(IModelEvent evt);
}