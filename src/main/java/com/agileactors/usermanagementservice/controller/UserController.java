package com.agileactors.usermanagementservice.controller;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.CreateUserResponseDto;
import com.agileactors.usermanagementservice.dto.GetUserResponseDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.dto.UpdateUserResponseDto;
import com.agileactors.usermanagementservice.exception.ApiException;
import com.agileactors.usermanagementservice.exception.InvalidArgumentException;
import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.service.UserService;
import com.agileactors.usermanagementservice.validations.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class responsible for getting the HTTP requests and passing them to
 * {@link com.agileactors.usermanagementservice.service.UserService}.
 */
@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;
  private final ConversionService conversionService;
  private final Validator validator;

  /**
   * Creates new instance of
   * {@link com.agileactors.usermanagementservice.controller.UserController}.
   *
   * @param userService The
   *                    {@link com.agileactors.usermanagementservice.service.UserService service}
   *                    to perform the requests
   * @param conversionService The {@link ConversionService conversion service} to convert the data
   *                          the {@link com.agileactors.usermanagementservice.service.UserService
   *                          service} returns
   * @param validator The {@link com.agileactors.usermanagementservice.validations.Validator} to
   *                  validate what's needed
   */
  public UserController(UserService userService,
                        ConversionService conversionService,
                        Validator validator) {
    this.userService = userService;
    this.conversionService = conversionService;
    this.validator = validator;
  }

  /**
   * Creates a new {@link com.agileactors.usermanagementservice.model.User user}, if
   * createUserRequestDto param is valid. If it's not, throws an
   * {@link com.agileactors.usermanagementservice.exception.InvalidArgumentException}.
   *
   * @param createUserRequestDto The
   *     {@link com.agileactors.usermanagementservice.dto.CreateUserRequestDto dto} holding
   *     {@link com.agileactors.usermanagementservice.model.User user's} to be created data
   * @param errors The errors valid annotation found
   *
   * @return The created {@link com.agileactors.usermanagementservice.model.User user}
   *
   * @throws InvalidArgumentException When createUserRequestDto failed to pass validation
   */
  @Operation(summary = "Create user",
             description = "Create a new user in DB. All fields must be filled and e-mail must "
                         + "contain '@' and a '.' afterwards.",
             tags = "POST")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
                   description = "User was successfully created",
                   content = { @Content
                               (mediaType = "application/json",
                                schema = @Schema(implementation = CreateUserResponseDto.class))}),
      @ApiResponse(responseCode = "400",
                   description = "User was NOT created due to invalid argument(s)",
                   content = { @Content (mediaType = "application/json",
                                         schema = @Schema (implementation = ApiException.class))})})
  @PostMapping(value = "/users")
  public CreateUserResponseDto createUser(@RequestBody @Valid
                                          CreateUserRequestDto createUserRequestDto,
                                          BindingResult errors) throws InvalidArgumentException {
    if (errors.hasErrors()) {
      throw new InvalidArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
    }
    validator.validateEmail(createUserRequestDto.email());
    return conversionService.convert(userService.createUser(createUserRequestDto, errors),
                                     CreateUserResponseDto.class);
  }

  /**
   * Retrieves all {@link com.agileactors.usermanagementservice.model.User users}. If searchTerm is
   * not blank, retrieves those whose first name contains the searchTerm.
   *
   * @param firstName The term that {@link com.agileactors.usermanagementservice.model.User user's}
   *                  first name must contain in order to be retrieved
   *
   * @return List with retrieved users
   */
  @Operation(summary = "Get all users",
             description = "Retrieve all users from DB. If parameter firstName exists, retrieve "
                         + "those whose first name contains the given value.",
             tags = "GET")
  @ApiResponse(responseCode = "200",
               description = "Returns a list with retrieved users. If no users were retrieved, "
                           + "list will be empty",
               content = { @Content (mediaType = "application/json",
                                     schema = @Schema (implementation = GetUserResponseDto.class))})
  @GetMapping(value = "/users")
  public List<GetUserResponseDto> getAllUsers(@RequestParam(defaultValue = "") String firstName) {
    List<GetUserResponseDto> getUserResponseDtoList = new ArrayList<>();
    userService.getAllUsers(firstName)
               .forEach(
                 user -> getUserResponseDtoList.add(
                           conversionService.convert(user, GetUserResponseDto.class)));
    return getUserResponseDtoList;
  }

  /**
   * Retrieves {@link com.agileactors.usermanagementservice.model.User user} with id userId.
   *
   * @param userId {@link com.agileactors.usermanagementservice.model.User User's} to be retrieved
   *               ID
   *
   * @return Retrieved {@link com.agileactors.usermanagementservice.model.User user}
   */
  @Operation(summary = "Get user by id",
             description = "Retrieve user with specific id",
             tags = "GET")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
                   description = "Returns retrieved user",
                   content = { @Content
                               (mediaType = "application/json",
                                schema = @Schema (implementation = CreateUserResponseDto.class))}),
      @ApiResponse(responseCode = "404",
                   description = "User with given ID does not exist",
                   content = { @Content
                               (mediaType = "application/json",
                                schema = @Schema (implementation = ApiException.class))})})
  @GetMapping(value = "/users/{id}")
  public GetUserResponseDto getUserById(@PathVariable(value = "id") UUID userId) {
    return conversionService.convert(userService.getUserById(userId), GetUserResponseDto.class);
  }

  /**
   * Removes {@link com.agileactors.usermanagementservice.model.User user} with id userId.
   *
   * @param userId The ID of {@link com.agileactors.usermanagementservice.model.User user} to be
   *               removed
   *
   * @return Number of {@link com.agileactors.usermanagementservice.model.User users} removed from
   *         DB
   */
  @Operation(summary = "Remove user",
             description = "Remove user with given id from DB",
             tags = "DELETE")
  @ApiResponse(responseCode = "200",
               description = "Returns number of users removed from DB",
               content = { @Content (mediaType = "application/json",
                                     schema = @Schema (implementation = Integer.class))}
  )
  @DeleteMapping(value = "/users/{id}")
  public int deleteUser(@PathVariable(value = "id") UUID userId) {
    return userService.deleteUser(userId);
  }

  /**
   * Removes all {@link com.agileactors.usermanagementservice.model.User users}.
   *
   * @return Number of {@link com.agileactors.usermanagementservice.model.User users} removed from
   *         DB
   */
  @Operation(summary = "Remove all users",
             description = "Removes all users",
             tags = "DELETE")
  @ApiResponse(responseCode = "200",
               description = "Returns number of users removed from DB",
               content = { @Content (mediaType = "application/json",
                                     schema = @Schema (implementation = Integer.class))})
  @DeleteMapping(value = "/users")
  public int deleteAllUsers() {
    return userService.deleteAllUsers();
  }

  /**
   * Updates {@link com.agileactors.usermanagementservice.model.User user} with id userId to the
   * values stored in updateUserRequestDto.
   *
   * @param userId The ID of {@link com.agileactors.usermanagementservice.model.User user} to be
   *               updated
   * @param updateUserRequestDto The
   *                      {@link com.agileactors.usermanagementservice.dto.UpdateUserRequestDto dto}
   *                      holding the new values
   * @param errors The errors valid annotation found
   *
   * @return All the info of the updated
   *         {@link com.agileactors.usermanagementservice.model.User user}
   *
   * @throws InvalidArgumentException When updateUserRequestDto fails to pass validation
   */
  @Operation(summary = "Update user",
             description = "Updates a user's basic information",
             tags = "PUT")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
                   description = "Returns updated user",
                   content = { @Content
                               (mediaType = "application/json",
                                schema = @Schema (implementation = CreateUserResponseDto.class))}),
      @ApiResponse(responseCode = "404",
                   description = "User with given ID does not exist",
                   content = { @Content (mediaType = "application/json",
                                         schema = @Schema (implementation = ApiException.class))}),
      @ApiResponse(responseCode = "400",
                  description = "User was NOT updated due to invalid argument(s)",
                  content = { @Content (mediaType = "application/json",
                                        schema = @Schema (implementation = ApiException.class))})
  })
  @PutMapping(value = "/users/{id}")
  public UpdateUserResponseDto updateUser(@PathVariable(value = "id") UUID userId,
                                          @RequestBody @Valid
                                          UpdateUserRequestDto updateUserRequestDto,
                                          BindingResult errors) throws InvalidArgumentException {
    if (errors.hasErrors()) {
      throw new InvalidArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
    }
    validator.validateEmail(updateUserRequestDto.email());
    UpdateUserRequestDto updatedUserDto = new UpdateUserRequestDto(userId,
                                                                   updateUserRequestDto.firstName(),
                                                                   updateUserRequestDto.lastName(),
                                                                   updateUserRequestDto.email());
    User updatedUser = userService.updateUser(updatedUserDto, errors);
    return conversionService.convert(updatedUser, UpdateUserResponseDto.class);
  }
}