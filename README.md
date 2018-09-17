# CP-ABE
pairing based cryptography

In the ciphertext-policy attribute-based encryption scheme, each user's private key (decryption key) is tied to a set of attributes 
representing that user's permissions. When a ciphertext is encrypted, a set of attributes is designated for the encryption,
and only users tied to the relevant attributes are able to decrypt the ciphertext. Unlike other Role-Based Access Control
(RBAC) systems, CPABE does not require a trusted authority, or any form of storage. The encryption itself serves as the RBAC
mechanism. A CPABE encryption scheme consists of four fundamental algorithms:-

 Setup
 Encrypt
 Key generation
 Decrypt

Setup:

The setup algorithm takes no input other than the implicit security parameter. It outputs the public parameters
PK and a master key MK.

Encrypt(PK,M, A):

The encryption algorithm takes as input the public parameters PK, a message M, and an access structure A over the universe
of attributes. The algorithm will encrypt M and produce a ciphertext CT such that only a user that possesses a set of
attributes that satisfies the access structure will be able to decrypt the message. We will assume that the ciphertext implicitly
contains A.

Key Generation(MK,S):

The key generation algorithm takes as input the master key MK and a set of attributes S that describe the key. It outputs 
a private key SK

Decrypt(PK, CT, SK):

The decryption algorithm takes as input the public parameters PK, a ciphertext CT, which contains an access policy A,
and a private key SK, which is a private key for a set S of attributes. If the set S of attributes satisfies the access structure A then the algorithm will decrypt the ciphertext and return a message M
