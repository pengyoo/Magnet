pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                metadata:
                  name: jenkins-agent
                spec:
                  containers:
                  - name: jnlp
                    image: jenkins/inbound-agent:latest
                  - name: maven
                    image: maven:3.9.2
                    command:
                    - cat
                    tty: true
                    volumeMounts:
                    - name: maven-repo
                      mountPath: /root/.m2
                  - name: kubectl
                    image: lachlanevenson/k8s-kubectl
                    command:
                    - cat
                    tty: true
                  - name: docker
                    image: docker:latest
                    command:
                    - cat
                    tty: true
                    volumeMounts:
                    - name: docker-socket
                      mountPath: /var/run/docker.sock
                  volumes:
                  - name: docker-socket
                    hostPath:
                      path: /var/run/docker.sock
                  - name: maven-repo
                    persistentVolumeClaim:
                      claimName: maven-repo-pvc
            '''
            defaultContainer 'jnlp'
        }
    }

    environment {
        DOCKER_IMAGE = 'ypydd88/magnet'
        K8S_NAMESPACE = 'demo'
        K8S_CREDENTIALS_ID = 'k3s'  // Replace with your Kubernetes credentials ID
        DOCKER_CREDENTIALS_ID = 'docker-hub-registry'  // Replace with your Docker Hub credentials ID
        SONARQUBE_CREDENTIALS_ID = 'sonarqube'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/pengyoo/Magnet.git'
            }
        }

        stage('Build and Test') {
            steps {
                container('maven') {
                    sh 'mvn -Dmaven.repo.local=/root/.m2/repository clean test package'
                }
            }
        }

       stage('SonarQube Analysis') {
           steps {
               container('maven') {
                   withSonarQubeEnv('SonarQube') {
                       withCredentials([string(credentialsId: "${SONARQUBE_CREDENTIALS_ID}", variable: 'SONAR_TOKEN')]) {
                           sh """
                               mvn sonar:sonar
                           """
                       }
                   }
               }
           }
       }

       stage('SonarQube Quality Gate') {
           steps {
               timeout(time: 10, unit: 'MINUTES') {
                   waitForQualityGate abortPipeline: true
               }
           }
       }

        stage('Build and Push Docker Image') {
            steps {
                container('docker') {
                    script {
                        docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                            def customImage = docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}")
                            customImage.push()
                            customImage.push('latest')
                        }
                    }
                }
            }
        }

        stage('Deploy to K3s') {
            steps {
                container('kubectl') {
                    withKubeConfig([credentialsId: "${K8S_CREDENTIALS_ID}", namespace: "${K8S_NAMESPACE}"]) {
                        script {
                            try {
                                // 检查命名空间是否存在，如果不存在则创建
                                sh """
                                    if ! kubectl get namespace ${K8S_NAMESPACE}; then
                                        kubectl create namespace ${K8S_NAMESPACE}
                                    fi
                                """

                                // 检查并应用 Kubernetes 资源文件
                                sh "ls -l kubernetes/"
                                sh "kubectl apply -f kubernetes/ --dry-run=client"
                                sh "kubectl apply -f kubernetes/"

                                // 更新部署镜像
                                sh "kubectl set image deployment/demo demo=${DOCKER_IMAGE}:${BUILD_NUMBER} -n ${K8S_NAMESPACE}"

                                // 等待部署完成
                                sh "kubectl rollout status deployment/demo -n ${K8S_NAMESPACE} --timeout=180s"

                                // 检查部署状态
                                sh "kubectl get deployment demo -n ${K8S_NAMESPACE} -o wide"
                                sh "kubectl get pods -n ${K8S_NAMESPACE} -l app=demo"
                            } catch (Exception e) {
                                echo "部署失败: ${e.getMessage()}"
                                sh "kubectl describe deployment demo -n ${K8S_NAMESPACE}"
                                sh "kubectl get events -n ${K8S_NAMESPACE} --sort-by=.metadata.creationTimestamp"
                                error "部署到 K3s 失败"
                            }
                        }
                    }
                }
            }
        }
    }
}