# README (French)

## Prérequis

Avant de démarrer le projet, assurez-vous d'avoir installé Docker et Docker Compose sur votre machine. Si vous utilisez Windows, vous pouvez utiliser Windows Subsystem for Linux (WSL) pour exécuter les commandes Docker.

### Utilisation de WSL pour Windows

Si vous utilisez Windows, je recommande d'utiliser Windows Subsystem for Linux (WSL) pour exécuter les commandes Docker de manière transparente. Pour installer WSL, suivez les instructions disponibles ici : [https://docs.microsoft.com/en-us/windows/wsl/install](https://docs.microsoft.com/en-us/windows/wsl/install)

## Démarrage du projet

Une fois Docker et Docker Compose installés, suivez les étapes ci-dessous pour démarrer le projet :

1. Ouvrez une fenêtre de terminal.
2. Accédez au répertoire du projet sur WSL ca sera `/mnt/c/Users/remplacer-cela-avec-votre-username/Desktop/ClientVacination`.
3. Exécutez la commande suivante pour construire et démarrer le projet :

```bash
sudo dockercompose up --build
```

Le projet sera accessible à l'adresse [http://localhost:8080](http://localhost:8080). Vous pouvez maintenant commencer à travailler sur le projet localement.
