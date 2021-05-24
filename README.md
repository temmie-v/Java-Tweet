twitter4j を利用して Twitter の API のリクエスト作成やレスポンス処理を行います。

動作にはこのREADME.mdが置かれているのと同じ場所に`twitter4j.properties`を置き、その中に自身の取得しているAPI keyなどを以下のように記述する必要があります。

```bash
debug=true
oauth.consumerKey='your API key'
oauth.consumerSecret='your API secret key'
oauth.accessToken='your access token'
oauth.accessTokenSecret='your access token secret'
loggerFactory=twitter4j.NullLoggerFactory
```

また、twitter4jに対応させる際には次のサイトを参照してください：https://twitter4j.org/ja/

---

各ファイルの動作は次の通りです。

- `GetFF.java`：`long myid = user_id;`で与えられたアカウントについて、フォロワーやフォロー中のアカウントの情報を`filepath/log.csv`に残します。
- `ListSearch.java`：`long lis = list_id;`で与えられたリストに入っているアカウントの情報を`filaepath/list.csv`に残します。
- `ScanTweet.java`：`long myid = user_id;`で与えられたアカウントについて、
  - ターミナルにまずタイムラインを文字で表示します。下にあるものほど新しくなっています。
  - その後は連続して投稿ができます。ツイートしたい内容を打ち改行すると添付する画像の枚数が問われ、0なら即時投稿、1から4ならば`JFileChooser`を用いてファイル選択ができその後投稿ができます。これは(無入力) → -1と入力するまで続けることが可能です。

