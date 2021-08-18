package com.iteale.industrialcase.core.network;

public interface IRpcProvider<V> {
    V executeRpc(Object... paramVarArgs);
}