package com.icooding.practice.designpattern;

import org.junit.Test;

/**
 * project_name icooding-practice
 * class Adapter
 * date  2017/11/8
 * author ibm
 * version 1.0
 */
public class Adapter {



    @Test
    public void adapter(){
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play("MP3","日落西山红下飞");
        audioPlayer.play("MP4","战狼");
        audioPlayer.play("VLC","一代天骄 努尔哈赤");
        audioPlayer.play("FLY","复仇者联盟");
        audioPlayer.play("ffs","克里米亚狂想曲");
    }




}
interface MediaPlayer {
    public void play(String audioType, String fileName);
}
interface AdvancedMediaPlayer {
    public void playVlc(String fileName);
    public void playMp4(String fileName);
    public void playFly(String fileName);
}
class VlcPlayer implements AdvancedMediaPlayer{
    @Override
    public void playVlc(String fileName) {
        System.out.println("播放 VLC 影片:"+fileName);
    }

    @Override
    public void playMp4(String fileName) {

    }

    @Override
    public void playFly(String fileName) {

    }
}

class MP4Player implements AdvancedMediaPlayer{
    @Override
    public void playVlc(String fileName) {
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("播放 MP4 影片:"+fileName);

    }

    @Override
    public void playFly(String fileName) {

    }
}


class FlyPlayer implements AdvancedMediaPlayer{
    @Override
    public void playVlc(String fileName) {
    }

    @Override
    public void playMp4(String fileName) {

    }

    @Override
    public void playFly(String fileName) {
        System.out.println("播放 Fly 影片:"+fileName);

    }
}

class MediaAdapter implements MediaPlayer{

    AdvancedMediaPlayer advancedMediaPlayer;


    public MediaAdapter(String audioType) {
        if(audioType.equals("MP4")){
            this.advancedMediaPlayer = new MP4Player();
        }else if(audioType.equals("FLY")){
            this.advancedMediaPlayer = new FlyPlayer();
        }else if(audioType.equals("VLC")){
            this.advancedMediaPlayer = new VlcPlayer();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equals("MP4")){
            advancedMediaPlayer.playMp4(fileName);
        }else if(audioType.equals("FLY")){
            advancedMediaPlayer.playFly(fileName);
        }else if(audioType.equals("VLC")){
            advancedMediaPlayer.playVlc(fileName);
        }else if(audioType.equals("MP3")){
            System.out.println("播放 MP3 歌曲:"+fileName);
        }else{
            System.out.println("不支持的播放格式 "+audioType+":"+fileName);
        }
    }



}

class  AudioPlayer implements MediaPlayer{
    MediaAdapter mediaAdapter;
    @Override
    public void play(String audioType, String fileName) {
        mediaAdapter = new MediaAdapter(audioType);
        mediaAdapter.play(audioType, fileName);
    }
}




