# twitter-data-correct

## 事前準備

予め下記をインストールしてください。

* Git
* Java1.8

下記はAmazonLinuxの例です。

```
sudo yum -y install git
sudo yum -y install java-1.8.0-openjdk-devel
sudo alternatives --config java  # Java1.8を選択
```

## デプロイ

対象ソースをcloneします。

`git clone https://github.com/areph/twitter-data-correct.git`


## 各種アクセスキー設定

`src/main/resources/setting.properties`
へTwitterのOAuth情報とAWS S3の情報を入力してください。TwitterのOAuthはTwitter Developers(https://dev.twitter.com/)から取得します。

ファイルに保存する件数もこちらで設定してください。あまり大きな件数を指定するとファイルを開くのが大変になりますので、1,000件ぐらいが良いと思います。


## 検索したいTwitterキーワード&アカウントを設定

キーワード検索をしたい場合は

src/main/resources/search-keyword-settings.csv

を編集してください。CSV形式となっており、複数行定義することが可能です。

```
オリンピック,#オリンピック,#リオ2016,#リオオリンピック
水泳,体操
```

特定アカウントのTweetを取得する場合は

src/main/resources/search-follow-settings.csv

を編集してください。CSV形式となっており、複数行定義することが可能です。

```
339200824,137655947
```

なお、検索ワードと特定アカウントのTweetはOR検索となっています。

## 実行

```bash
cd twitter-data-correct
./start.sh
```

現在のTwitterストリームから指定したキーワードを取得し続け、指定した件数になるとS3へCSVファイルをアップロードし続けます。

## 停止方法

Ctrl+c で停止します。
