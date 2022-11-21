package com.yagl.hooks;

public interface Hookable {

    <T> StateHook<T> useState(Class<T> clazz, T defaultValue);

    void invalidateHook(StateHook<?> hook);

    EffectHook useEffect(Runnable onUpdate, StateHook<?>... hooks);

    OnOpenHook useOnOpen(Runnable onOpen);

    OnCloseHook useOnClose(Runnable onClose);
}
