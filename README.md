# twitter-data-correct

## 事前準備
* Gitをインストール
* Java1.8をインストール

下記はAmazonLinuxの例
```
sudo yum -y install git
sudo yum -y install java-1.8.0-openjdk-devel
sudo alternatives --config java  # Java1.8を選択
```

## デプロイ

対象ソースをclone

`git clone https://github.com/areph/twitter-data-correct.git`


## 各種アクセスキー設定

`src/main/resources/setting.properties`
へTwitterのOAuth情報とAWS S3の情報を入力


## 検索したいTwitterキーワード&アカウントを設定

`vi src/main/java/org/work/twitter/TwitterSearcher.java`
```java
// 取得したいキーワードを指定
private static final String[] TRACK = new String[]{ "オリンピック", "#オリンピック", "#リオ2016", "#リオオリンピック" };
// 取得したいアカウントのTwetterIDを指定
private static final long[] FOLLOW = new long[]{ 53929093 };
// ファイルに保存するツイート数
private static final int SAVE_FILE_TWEET_COUNT = 10;
```

## 実行

```bash
cd twitter-data-correct
./start.sh
```
