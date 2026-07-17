#!/bin/bash
# Project Structure Generator Script
# Verifies the complete component architecture is in place

echo "============================================"
echo "ChattMate Spring Boot - Architecture Check"
echo "============================================"
echo ""

# Define color codes
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to check if directory exists
check_dir() {
    if [ -d "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
    else
        echo -e "${RED}✗${NC} $1 (MISSING)"
    fi
}

# Function to check if file exists
check_file() {
    if [ -f "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
    else
        echo -e "${RED}✗${NC} $1 (MISSING)"
    fi
}

echo "Checking Directory Structure..."
echo ""

# Main directories
echo "Main Directories:"
check_dir "src/main/java/com/chattmate/api"
check_dir "src/main/resources"
check_dir "src/test/java/com/chattmate/api"

echo ""
echo "Component Packages:"
check_dir "src/main/java/com/chattmate/api/controller"
check_dir "src/main/java/com/chattmate/api/service"
check_dir "src/main/java/com/chattmate/api/repository"
check_dir "src/main/java/com/chattmate/api/model/entity"
check_dir "src/main/java/com/chattmate/api/model/dto"
check_dir "src/main/java/com/chattmate/api/model/enum"
check_dir "src/main/java/com/chattmate/api/config"
check_dir "src/main/java/com/chattmate/api/exception"
check_dir "src/main/java/com/chattmate/api/security"
check_dir "src/main/java/com/chattmate/api/middleware"
check_dir "src/main/java/com/chattmate/api/validation"
check_dir "src/main/java/com/chattmate/api/messaging"
check_dir "src/main/java/com/chattmate/api/streaming"
check_dir "src/main/java/com/chattmate/api/util"

echo ""
echo "Java Files:"
check_file "src/main/java/com/chattmate/api/ChattMateApplication.java"
check_file "src/main/java/com/chattmate/api/model/entity/User.java"
check_file "src/main/java/com/chattmate/api/model/enum/KycStatus.java"
check_file "src/main/java/com/chattmate/api/model/dto/UserDTO.java"
check_file "src/main/java/com/chattmate/api/repository/UserRepository.java"
check_file "src/main/java/com/chattmate/api/service/ValidationService.java"
check_file "src/main/java/com/chattmate/api/service/LiveKitTokenService.java"
check_file "src/main/java/com/chattmate/api/controller/UserController.java"
check_file "src/main/java/com/chattmate/api/config/CorsConfig.java"
check_file "src/main/java/com/chattmate/api/exception/GlobalExceptionHandler.java"
check_file "src/main/java/com/chattmate/api/exception/ResourceNotFoundException.java"
check_file "src/main/java/com/chattmate/api/exception/ValidationException.java"
check_file "src/main/java/com/chattmate/api/exception/UnauthorizedException.java"
check_file "src/main/java/com/chattmate/api/util/ResponseUtil.java"

echo ""
echo "Configuration Files:"
check_file "src/main/resources/application.properties"
check_file "src/main/resources/application-dev.properties"
check_file "src/main/resources/application-prod.properties"

echo ""
echo "Documentation Files:"
check_file "ARCHITECTURE.md"
check_file "QUICK_START.md"

echo ""
echo "Build Files:"
check_file "pom.xml"

echo ""
echo "============================================"
echo "Architecture Check Complete!"
echo "============================================"
