package com.owo.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.owo.app.R;

public class ImageLoadUtil {

	public static DisplayImageOptions option(int outWidth, int outHeight) {
		Options options = new Options();
		options.outWidth = outWidth;
		options.outHeight = outHeight;
		return new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_app_logo) // 设置图片在下载期间显示的图片
				// .showImageForEmptyUri(R.drawable.ic_app_logo)//
				// 设置图片Uri为空或是错误的时候显示的图片
				// .showImageOnFail(R.drawable.ic_app_logo) //
				// 设置图片加载/解码过程中错误时候显示的图片
				// .cacheInMemory(false)// 设置下载的图片是否缓存在内存中
				// .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				// .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
				// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.decodingOptions(options)
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(10, 10))//
				// 是否设置为圆角，弧度为多少
				// .displayer(new FadeInBitmapDisplayer(200))// 是否图片加载好后渐入的动画时间
				// 构建完成
				.build();
	}
}
