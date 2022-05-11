package com.que.shot.assets;

import android.graphics.Bitmap;

import org.wysaid.common.SharedContext;
import org.wysaid.nativePort.CGEImageHandler;

import java.util.ArrayList;
import java.util.List;

public class EffectCodeAsset {

    public static class EffectsCode {
        private String image;
        private String name;

        EffectsCode(String image, String name) {
            this.image = image;
            this.name = name;
        }

        public String getImage() {
            return this.image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static final EffectsCode[] DODGE_EFFECTS = {
            new EffectsCode("", "none"),
            new EffectsCode("#unpack @krblend cd effect/scr1.jpg 100", "dodge 1"),
            new EffectsCode("#unpack @krblend cd effect/scr2.jpg 100", "dodge 2"),
            new EffectsCode("#unpack @krblend cd effect/scr3.jpg 100", "dodge 3"),
            new EffectsCode("#unpack @krblend cd effect/scr4.jpg 100", "dodge 4"),
            new EffectsCode("#unpack @krblend cd effect/scr5.jpg 100", "dodge 5"),
            new EffectsCode("#unpack @krblend cd effect/scr6.jpg 100", "dodge 6"),
            new EffectsCode("#unpack @krblend cd effect/scr7.jpg 100", "dodge 7"),
            new EffectsCode("#unpack @krblend cd effect/scr8.jpg 100", "dodge 8"),
            new EffectsCode("#unpack @krblend cd effect/scr9.jpg 100", "dodge 9"),
            new EffectsCode("#unpack @krblend cd effect/scr10.jpg 100", "dodge 10"),
            new EffectsCode("#unpack @krblend cd effect/scr11.jpg 100", "dodge 11"),
            new EffectsCode("#unpack @krblend cd effect/scr12.jpg 100", "dodge 12"),
            new EffectsCode("#unpack @krblend cd effect/scr13.jpg 100", "dodge 13"),
            new EffectsCode("#unpack @krblend cd effect/scr14.jpg 100", "dodge 14"),
            new EffectsCode("#unpack @krblend cd effect/scr15.jpg 100", "dodge 15"),
            new EffectsCode("#unpack @krblend cd effect/scr16.jpg 100", "dodge 16"),
            new EffectsCode("#unpack @krblend cd effect/scr17.jpg 100", "dodge 17"),
            new EffectsCode("#unpack @krblend cd effect/scr18.jpg 100", "dodge 18"),
            new EffectsCode("#unpack @krblend cd effect/scr19.jpg 100", "dodge 19"),
            new EffectsCode("#unpack @krblend cd effect/scr20.jpg 100", "dodge 20"),
            new EffectsCode("#unpack @krblend cd effect/scr21.jpg 100", "dodge 21"),
            new EffectsCode("#unpack @krblend cd effect/scr22.jpg 100", "dodge 22"),
            new EffectsCode("#unpack @krblend cd effect/scr23.jpg 100", "dodge 23"),
            new EffectsCode("#unpack @krblend cd effect/scr24.jpg 100", "dodge 24"),
            new EffectsCode("#unpack @krblend cd effect/scr25.jpg 100", "dodge 25"),
            new EffectsCode("#unpack @krblend cd effect/scr26.jpg 100", "dodge 26"),
            new EffectsCode("#unpack @krblend cd effect/scr27.jpg 100", "dodge 27"),
            new EffectsCode("#unpack @krblend cd effect/scr28.jpg 100", "dodge 28"),
            new EffectsCode("#unpack @krblend cd effect/scr29.jpg 100", "dodge 29"),
            new EffectsCode("#unpack @krblend cd effect/scr30.jpg 100", "dodge 30"),
            new EffectsCode("#unpack @krblend cd effect/scr31.jpg 100", "dodge 31"),
            new EffectsCode("#unpack @krblend cd effect/scr32.jpg 100", "dodge 32"),
            new EffectsCode("#unpack @krblend cd effect/scr33.jpg 100", "dodge 33"),
            new EffectsCode("#unpack @krblend cd effect/scr34.jpg 100", "dodge 34"),
            new EffectsCode("#unpack @krblend cd effect/scr35.jpg 100", "dodge 35"),
            new EffectsCode("#unpack @krblend cd effect/scr36.jpg 100", "dodge 36")
    };

    public static final EffectsCode[] OVERLAY_EFFECTS = {
            new EffectsCode("", "none"),
            new EffectsCode("#unpack @krblend sr overlay/scr1.jpg 100", "overlay 1"),
            new EffectsCode("#unpack @krblend sr overlay/scr2.jpg 100", "overlay 2"),
            new EffectsCode("#unpack @krblend sr overlay/scr3.jpg 100", "overlay 3"),
            new EffectsCode("#unpack @krblend sr overlay/scr4.jpg 100", "overlay 4"),
            new EffectsCode("#unpack @krblend sr overlay/scr5.jpg 100", "overlay 5"),
            new EffectsCode("#unpack @krblend sr overlay/scr6.jpg 100", "overlay 6"),
            new EffectsCode("#unpack @krblend sr overlay/scr7.jpg 100", "overlay 7"),
            new EffectsCode("#unpack @krblend sr overlay/scr8.jpg 100", "overlay 8"),
            new EffectsCode("#unpack @krblend sr overlay/scr9.jpg 100", "overlay 9"),
            new EffectsCode("#unpack @krblend sr overlay/scr10.jpg 100", "overlay 10"),
            new EffectsCode("#unpack @krblend sr overlay/scr11.jpg 100", "overlay 11"),
            new EffectsCode("#unpack @krblend sr overlay/scr12.jpg 100", "overlay 12"),
            new EffectsCode("#unpack @krblend sr overlay/scr13.jpg 100", "overlay 13"),
            new EffectsCode("#unpack @krblend sr overlay/scr14.jpg 100", "overlay 14"),
            new EffectsCode("#unpack @krblend sr overlay/scr15.jpg 100", "overlay 15"),
            new EffectsCode("#unpack @krblend sr overlay/scr16.jpg 100", "overlay 16"),
            new EffectsCode("#unpack @krblend sr overlay/scr17.jpg 100", "overlay 17"),
            new EffectsCode("#unpack @krblend sr overlay/scr18.jpg 100", "overlay 18"),
            new EffectsCode("#unpack @krblend sr overlay/scr19.jpg 100", "overlay 19"),
            new EffectsCode("#unpack @krblend sr overlay/scr20.jpg 100", "overlay 20"),
            new EffectsCode("#unpack @krblend sr overlay/scr21.jpg 100", "overlay 21"),
            new EffectsCode("#unpack @krblend sr overlay/scr22.jpg 100", "overlay 22"),
            new EffectsCode("#unpack @krblend sr overlay/scr23.jpg 100", "overlay 23")
    };

    public static final EffectsCode[] HARDMIX_EFFECTS = {
            new EffectsCode("", "none"),
            new EffectsCode("#unpack @krblend hm effect/scr1.jpg 100", "Hardmix 1"),
            new EffectsCode("#unpack @krblend hm effect/scr2.jpg 100", "Hardmix 2"),
            new EffectsCode("#unpack @krblend hm effect/scr3.jpg 100", "Hardmix 3"),
            new EffectsCode("#unpack @krblend hm effect/scr4.jpg 100", "Hardmix 4"),
            new EffectsCode("#unpack @krblend hm effect/scr5.jpg 100", "Hardmix 5"),
            new EffectsCode("#unpack @krblend hm effect/scr6.jpg 100", "Hardmix 6"),
            new EffectsCode("#unpack @krblend hm effect/scr7.jpg 100", "Hardmix 7"),
            new EffectsCode("#unpack @krblend hm effect/scr8.jpg 100", "Hardmix 8"),
            new EffectsCode("#unpack @krblend hm effect/scr9.jpg 100", "Hardmix 9"),
            new EffectsCode("#unpack @krblend hm effect/scr10.jpg 100", "Hardmix 10"),
            new EffectsCode("#unpack @krblend hm effect/scr11.jpg 100", "Hardmix 11"),
            new EffectsCode("#unpack @krblend hm effect/scr12.jpg 100", "Hardmix 12"),
            new EffectsCode("#unpack @krblend hm effect/scr13.jpg 100", "Hardmix 13"),
            new EffectsCode("#unpack @krblend hm effect/scr14.jpg 100", "Hardmix 14"),
            new EffectsCode("#unpack @krblend hm effect/scr15.jpg 100", "Hardmix 15"),
            new EffectsCode("#unpack @krblend hm effect/scr16.jpg 100", "Hardmix 16"),
            new EffectsCode("#unpack @krblend hm effect/scr17.jpg 100", "Hardmix 17"),
            new EffectsCode("#unpack @krblend hm effect/scr18.jpg 100", "Hardmix 18"),
            new EffectsCode("#unpack @krblend hm effect/scr19.jpg 100", "Hardmix 19"),
            new EffectsCode("#unpack @krblend hm effect/scr20.jpg 100", "Hardmix 20"),
            new EffectsCode("#unpack @krblend hm effect/scr21.jpg 100", "Hardmix 21"),
            new EffectsCode("#unpack @krblend hm effect/scr22.jpg 100", "Hardmix 22"),
            new EffectsCode("#unpack @krblend hm effect/scr23.jpg 100", "Hardmix 23"),
            new EffectsCode("#unpack @krblend hm effect/scr24.jpg 100", "Hardmix 24"),
            new EffectsCode("#unpack @krblend hm effect/scr25.jpg 100", "Hardmix 25"),
            new EffectsCode("#unpack @krblend hm effect/scr26.jpg 100", "Hardmix 26"),
            new EffectsCode("#unpack @krblend hm effect/scr27.jpg 100", "Hardmix 27"),
            new EffectsCode("#unpack @krblend hm effect/scr28.jpg 100", "Hardmix 28"),
            new EffectsCode("#unpack @krblend hm effect/scr29.jpg 100", "Hardmix 29"),
            new EffectsCode("#unpack @krblend hm effect/scr30.jpg 100", "Hardmix 30"),
            new EffectsCode("#unpack @krblend hm effect/scr31.jpg 100", "Hardmix 31"),
            new EffectsCode("#unpack @krblend hm effect/scr32.jpg 100", "Hardmix 32"),
            new EffectsCode("#unpack @krblend hm effect/scr33.jpg 100", "Hardmix 33"),
            new EffectsCode("#unpack @krblend hm effect/scr34.jpg 100", "Hardmix 34"),
            new EffectsCode("#unpack @krblend hm effect/scr35.jpg 100", "Hardmix 35"),
            new EffectsCode("#unpack @krblend hm effect/scr36.jpg 100", "Hardmix 36")
    };

    public static final EffectsCode[] DIVIDE_EFFECTS = {
            new EffectsCode("", "none"),
            new EffectsCode("#unpack @krblend div effect/scr1.jpg 100", "Divide 1"),
            new EffectsCode("#unpack @krblend div effect/scr2.jpg 100", "Divide 2"),
            new EffectsCode("#unpack @krblend div effect/scr3.jpg 100", "Divide 3"),
            new EffectsCode("#unpack @krblend div effect/scr4.jpg 100", "Divide 4"),
            new EffectsCode("#unpack @krblend div effect/scr5.jpg 100", "Divide 5"),
            new EffectsCode("#unpack @krblend div effect/scr6.jpg 100", "Divide 6"),
            new EffectsCode("#unpack @krblend div effect/scr7.jpg 100", "Divide 7"),
            new EffectsCode("#unpack @krblend div effect/scr8.jpg 100", "Divide 8"),
            new EffectsCode("#unpack @krblend div effect/scr9.jpg 100", "Divide 9"),
            new EffectsCode("#unpack @krblend div effect/scr10.jpg 100", "Divide 10"),
            new EffectsCode("#unpack @krblend div effect/scr11.jpg 100", "Divide 11"),
            new EffectsCode("#unpack @krblend div effect/scr12.jpg 100", "Divide 12"),
            new EffectsCode("#unpack @krblend div effect/scr13.jpg 100", "Divide 13"),
            new EffectsCode("#unpack @krblend div effect/scr14.jpg 100", "Divide 14"),
            new EffectsCode("#unpack @krblend div effect/scr15.jpg 100", "Divide 15"),
            new EffectsCode("#unpack @krblend div effect/scr16.jpg 100", "Divide 16"),
            new EffectsCode("#unpack @krblend div effect/scr17.jpg 100", "Divide 17"),
            new EffectsCode("#unpack @krblend div effect/scr18.jpg 100", "Divide 18"),
            new EffectsCode("#unpack @krblend div effect/scr19.jpg 100", "Divide 19"),
            new EffectsCode("#unpack @krblend div effect/scr20.jpg 100", "Divide 20"),
            new EffectsCode("#unpack @krblend div effect/scr21.jpg 100", "Divide 21"),
            new EffectsCode("#unpack @krblend div effect/scr22.jpg 100", "Divide 22"),
            new EffectsCode("#unpack @krblend div effect/scr23.jpg 100", "Divide 23"),
            new EffectsCode("#unpack @krblend div effect/scr24.jpg 100", "Divide 24"),
            new EffectsCode("#unpack @krblend div effect/scr25.jpg 100", "Divide 25"),
            new EffectsCode("#unpack @krblend div effect/scr26.jpg 100", "Divide 26"),
            new EffectsCode("#unpack @krblend div effect/scr27.jpg 100", "Divide 27"),
            new EffectsCode("#unpack @krblend div effect/scr28.jpg 100", "Divide 28"),
            new EffectsCode("#unpack @krblend div effect/scr29.jpg 100", "Divide 29"),
            new EffectsCode("#unpack @krblend div effect/scr30.jpg 100", "Divide 30"),
            new EffectsCode("#unpack @krblend div effect/scr31.jpg 100", "Divide 31"),
            new EffectsCode("#unpack @krblend div effect/scr32.jpg 100", "Divide 32"),
            new EffectsCode("#unpack @krblend div effect/scr33.jpg 100", "Divide 33"),
            new EffectsCode("#unpack @krblend div effect/scr34.jpg 100", "Divide 34"),
            new EffectsCode("#unpack @krblend div effect/scr35.jpg 100", "Divide 35"),
            new EffectsCode("#unpack @krblend div effect/scr36.jpg 100", "Divide 36")
    };

    public static final EffectsCode[] COLOR_EFFECTS = {
            new EffectsCode("", "none"),
            new EffectsCode("#unpack @krblend cl effect/scr1.jpg 100", "Burn 1"),
            new EffectsCode("#unpack @krblend cl effect/scr2.jpg 100", "Burn 2"),
            new EffectsCode("#unpack @krblend cl effect/scr3.jpg 100", "Burn 3"),
            new EffectsCode("#unpack @krblend cl effect/scr4.jpg 100", "Burn 4"),
            new EffectsCode("#unpack @krblend cl effect/scr5.jpg 100", "Burn 5"),
            new EffectsCode("#unpack @krblend cl effect/scr6.jpg 100", "Burn 6"),
            new EffectsCode("#unpack @krblend cl effect/scr7.jpg 100", "Burn 7"),
            new EffectsCode("#unpack @krblend cl effect/scr8.jpg 100", "Burn 8"),
            new EffectsCode("#unpack @krblend cl effect/scr9.jpg 100", "Burn 9"),
            new EffectsCode("#unpack @krblend cl effect/scr10.jpg 100", "Burn 10"),
            new EffectsCode("#unpack @krblend cl effect/scr11.jpg 100", "Burn 11"),
            new EffectsCode("#unpack @krblend cl effect/scr12.jpg 100", "Burn 12"),
            new EffectsCode("#unpack @krblend cl effect/scr13.jpg 100", "Burn 13"),
            new EffectsCode("#unpack @krblend cl effect/scr14.jpg 100", "Burn 14"),
            new EffectsCode("#unpack @krblend cl effect/scr15.jpg 100", "Burn 15"),
            new EffectsCode("#unpack @krblend cl effect/scr16.jpg 100", "Burn 16"),
            new EffectsCode("#unpack @krblend cl effect/scr17.jpg 100", "Burn 17"),
            new EffectsCode("#unpack @krblend cl effect/scr18.jpg 100", "Burn 18"),
            new EffectsCode("#unpack @krblend cl effect/scr19.jpg 100", "Burn 19"),
            new EffectsCode("#unpack @krblend cl effect/scr20.jpg 100", "Burn 20"),
            new EffectsCode("#unpack @krblend cl effect/scr21.jpg 100", "Burn 21"),
            new EffectsCode("#unpack @krblend cl effect/scr22.jpg 100", "Burn 22"),
            new EffectsCode("#unpack @krblend cl effect/scr23.jpg 100", "Burn 23"),
            new EffectsCode("#unpack @krblend cl effect/scr24.jpg 100", "Burn 24"),
            new EffectsCode("#unpack @krblend cl effect/scr25.jpg 100", "Burn 25"),
            new EffectsCode("#unpack @krblend cl effect/scr26.jpg 100", "Burn 26"),
            new EffectsCode("#unpack @krblend cl effect/scr27.jpg 100", "Burn 27"),
            new EffectsCode("#unpack @krblend cl effect/scr28.jpg 100", "Burn 28"),
            new EffectsCode("#unpack @krblend cl effect/scr29.jpg 100", "Burn 29"),
            new EffectsCode("#unpack @krblend cl effect/scr30.jpg 100", "Burn 30"),
            new EffectsCode("#unpack @krblend cl effect/scr31.jpg 100", "Burn 31"),
            new EffectsCode("#unpack @krblend cl effect/scr32.jpg 100", "Burn 32"),
            new EffectsCode("#unpack @krblend cl effect/scr33.jpg 100", "Burn 33"),
            new EffectsCode("#unpack @krblend cl effect/scr34.jpg 100", "Burn 34"),
            new EffectsCode("#unpack @krblend cl effect/scr35.jpg 100", "Burn 35"),
            new EffectsCode("#unpack @krblend cl effect/scr36.jpg 100", "Burn 36")
    };

    public static List<Bitmap> getListBitmapDodgeEffect(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        SharedContext sharedContext = SharedContext.create();
        sharedContext.makeCurrent();
        CGEImageHandler cgeImageHandler = new CGEImageHandler();
        cgeImageHandler.initWithBitmap(bitmap);
        for (EffectsCode filterBean : DODGE_EFFECTS) {
            cgeImageHandler.setFilterWithConfig(filterBean.getImage());
            cgeImageHandler.processFilters();
            arrayList.add(cgeImageHandler.getResultBitmap());
        }
        sharedContext.release();
        return arrayList;
    }


    public static List<Bitmap> getListBitmapOverlayEffect(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        SharedContext sharedContext = SharedContext.create();
        sharedContext.makeCurrent();
        CGEImageHandler cgeImageHandler = new CGEImageHandler();
        cgeImageHandler.initWithBitmap(bitmap);
        for (EffectsCode filterBean : OVERLAY_EFFECTS) {
            cgeImageHandler.setFilterWithConfig(filterBean.getImage());
            cgeImageHandler.processFilters();
            arrayList.add(cgeImageHandler.getResultBitmap());
        }
        sharedContext.release();
        return arrayList;
    }

    public static List<Bitmap> getListBitmapHardmixEffect(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        SharedContext sharedContext = SharedContext.create();
        sharedContext.makeCurrent();
        CGEImageHandler cgeImageHandler = new CGEImageHandler();
        cgeImageHandler.initWithBitmap(bitmap);
        for (EffectsCode filterBean : HARDMIX_EFFECTS) {
            cgeImageHandler.setFilterWithConfig(filterBean.getImage());
            cgeImageHandler.processFilters();
            arrayList.add(cgeImageHandler.getResultBitmap());
        }
        sharedContext.release();
        return arrayList;
    }
    public static List<Bitmap> getListBitmapDivideEffect(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        SharedContext sharedContext = SharedContext.create();
        sharedContext.makeCurrent();
        CGEImageHandler cgeImageHandler = new CGEImageHandler();
        cgeImageHandler.initWithBitmap(bitmap);
        for (EffectsCode filterBean : DIVIDE_EFFECTS) {
            cgeImageHandler.setFilterWithConfig(filterBean.getImage());
            cgeImageHandler.processFilters();
            arrayList.add(cgeImageHandler.getResultBitmap());
        }
        sharedContext.release();
        return arrayList;
    }

    public static List<Bitmap> getListBitmapColorEffect(Bitmap bitmap) {
        ArrayList arrayList = new ArrayList();
        SharedContext sharedContext = SharedContext.create();
        sharedContext.makeCurrent();
        CGEImageHandler cgeImageHandler = new CGEImageHandler();
        cgeImageHandler.initWithBitmap(bitmap);
        for (EffectsCode filterBean : COLOR_EFFECTS) {
            cgeImageHandler.setFilterWithConfig(filterBean.getImage());
            cgeImageHandler.processFilters();
            arrayList.add(cgeImageHandler.getResultBitmap());
        }
        sharedContext.release();
        return arrayList;
    }
}
