package dev.olog.basil.app;

import com.bumptech.glide.module.AppGlideModule;

@com.bumptech.glide.annotation.GlideModule
public class GlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
