CURRENT_VERSION := 1.4.6
OLD_VERSION := 1.0.0
DOCKER_IMAGE_NAME := suivi-tache-back:$(CURRENT_VERSION)
DOCKER_ARCHIVE_FILE_NAME := suivi-tache-back.$(CURRENT_VERSION).tar
version:=1.0.0
releaseMessage="Build release v${version}"

SSH_TEESE_PORT ?= 4000
SSH_TEESE_HOST ?= 51.83.74.128
SSH_TEESE_USER ?= teese

isDocker := $(shell docker info > /dev/null 2>&1 && echo 1)

ifeq ($(isDocker), 1)
	start_docker :=
else
	start_docker := sudo systemctl start docker
endif

.DEFAULT_GOAL := help
.PHONY: help
help: ## Affiche cette aide
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: start-docker
start-docker: ## Démarre le service docker s'il n'est pas encore démarré
	$(start_docker)

.PHONY: clear
clear: ## Efface le dossier target
	rm -rf target

.PHONY: build
build: pom.xml clear ## Génère le fichier .jar dans le dossier target
	./mvnw clean package -Dmaven.test.skip=true

.PHONY: build-docker
build-docker: build start-docker ## Supprime l'image docker si elle existe et Construit une nouvelle image
	docker rmi -f $(DOCKER_IMAGE_NAME)
	docker build -t $(DOCKER_IMAGE_NAME) .

.PHONY: save-docker
save-docker: build-docker ## Sauvegarde le fichier .tar à envoyer sur le serveur
	cd target && docker save $(DOCKER_IMAGE_NAME) > $(DOCKER_ARCHIVE_FILE_NAME)

.PHONY: push-docker-image
push-docker-image: save-docker ## Copie le fichier .tar généré sur le serveur via rsync
	rsync -avhPz -e 'ssh -p $(SSH_TEESE_PORT)' ./target/$(DOCKER_ARCHIVE_FILE_NAME) $(SSH_TEESE_USER)@$(SSH_TEESE_HOST):/home/teese

.PHONY: deploy
deploy: push-docker-image ## Lance les procédures de déploiement [make deploy CURRENT_VERSION=1.4.6 CUSTOMER=catramp]

