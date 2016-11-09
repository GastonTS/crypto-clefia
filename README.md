#CLEFIA
CLEFIA is a proprietary block cipher algorithm, developed by Sony. Its name is derived from the French word clef, meaning "key".

The **block size is 128 bits** (16 bytes) and the key size can be 128 bit, 192 bit or 256 bit. It is intended to be used in DRM systems. It is among the cryptographic techniques recommended candidate for Japanese government use by CRYPTREC revision in 2013.

##Implementation
This is a practical work for UTN-FRBA assignature Cryptography, there are several points that can be optimized to get a better performance but the main focus was make readable code, easy to understand and similar to the official refference text.

##Usage
 * From SBT acess scala console
 * From scala console load the files in this order
  * :load src/main/scala/clefia/Numeric.scala
  * :load src/main/scala/clefia/GFN.scala
  * :load src/main/scala/clefia/KeyScheduling.scala
  * :load src/main/scala/clefia/DataProcessing.scala
  * :load src/main/scala/clefia/Clefia.scala

##Examples
###Blocks
```
scala> Clefia.encryptBlock((72637, 2836758, 19375, 28476), (283675, 92784, 28375, 278575, 23857, 48657))
Begin Parallel Encryption
Keys Generated
res0: clefia.Numeric.Numeric128 = (-276948526,-693155952,68681714,137408418)


scala> Clefia.decryptBlock((-276948526,-693155952,68681714,137408418), (283675, 92784, 28375, 278575, 23857, 48657))
Begin Parallel Encryption
Keys Generated
res1: clefia.Numeric.Numeric128 = (72637,2836758,19375,28476)
```

###Strings
```
scala> ChainedClefia.encryptText("Esto es el texto claro", "Esta es la llave")
Begin Chained Encryption
Processed Blocks: 3/3 (100%)
Duration: 0.064560108s
res2: String = 䣙鵖Ň償욠⸂괚謲嬢罬翛㌜表⺬ヸല祐Ț摠ḫ퀲쇨⛌

scala> ChainedClefia.decryptText(res2, "Esta es la llave")
Begin Chained Encryption
Processed Blocks: 3/3 (100%)
Duration: 0.022810721000000003s
res3: String = Esto es el texto claro
```

###Files
```
scala> Clefia.encryptFile("src/test/resources/garden.bmp", "src/test/resources/encryptedFile.bmp", "Esta es otra la llave!!!")
Begin Parallel Encryption
Keys Generated
Encrypted File: src/test/resources/encryptedFile.bmp
Duration: 10.217996271s
res4: String = src/test/resources/encryptedFile.bmp


scala> Clefia.decryptFile("src/test/resources/encryptedFile.bmp", "src/test/resources/decryptedFile.bmp", "Esta es otra la llave!!!")
Begin Parallel Encryption
Keys Generated
Decrypted File: src/test/resources/decryptedFile.bmp
Duration: 7.148700861s
res5: String = src/test/resources/decryptedFile.bmp
```

###BMP
```
scala> Clefia.encryptBMP("src/test/resources/batman.bmp", "src/test/resources/batmanPE.bmp", "Llave Secreta!!!")
Begin Parallel Encryption
Keys Generated
Encrypted BMP: src/test/resources/batmanPE.bmp
Duration: 0.891456919s
res6: String = src/test/resources/batmanPE.bmp


scala> Clefia.decryptBMP("src/test/resources/batmanPE.bmp", "src/test/resources/batmanPD.bmp", "Llave Secreta!!!")
Begin Parallel Encryption
Keys Generated
Decrypted BMP: src/test/resources/batmanPD.bmp
Duration: 0.7113750870000001s
res7: String = src/test/resources/batmanPD.bmp


scala> ChainedClefia.encryptBMP("src/test/resources/batman.bmp", "src/test/resources/batmanCE.bmp", "Llave Secreta!!!")
Begin Chained Encryption
Processed Blocks: 11251/11251 (100%)
Encrypted BMP: src/test/resources/batmanCE.bmp
Duration: 2.5844998s
res8: String = src/test/resources/batmanCE.bmp


scala> ChainedClefia.decryptBMP("src/test/resources/batmanCE.bmp", "src/test/resources/batmanCD.bmp", "Llave Secreta!!!")
Begin Chained Encryption
Processed Blocks: 11251/11251 (100%)
Decrypted BMP: src/test/resources/batmanCD.bmp
Duration: 2.321788317s
res9: String = src/test/resources/batmanCD.bmp
```

